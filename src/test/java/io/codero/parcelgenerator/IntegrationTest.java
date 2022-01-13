package io.codero.parcelgenerator;

import io.codero.parcelgenerator.enity.Parcel;
import io.codero.parcelgenerator.service.ParcelProducerService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.ClassRule;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled
@SpringBootTest(classes = ParcelGeneratorApp.class)
@DirtiesContext
public class IntegrationTest {
    @ClassRule
    public static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    @Autowired
    private ParcelProducerService service;

    @Value("${kafka.topic}")
    private String topic;

    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "groupId");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return props;
    }

    @Test
    public void sendToKafkaMessageTest() {
        kafka.start();

        Parcel parcel = Parcel.builder()
                .timestamp(Instant.now())
                .idReceiver(1000)
                .sender("testsender")
                .build();
        KafkaConsumer<String, Parcel> consumer = new KafkaConsumer<>(consumerConfigs());
        consumer.subscribe(singletonList(topic));

        service.sendMessage(parcel);


        assertNotNull(consumer.poll(Duration.ofMillis(10000)));
    }
}
