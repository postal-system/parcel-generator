package io.codero.parcelgenerator;

import io.codero.parcelgenerator.config.ConsumerConfigTest;
import io.codero.parcelgenerator.enity.Parcel;
import io.codero.parcelgenerator.initializer.KafkaContainers;
import io.codero.parcelgenerator.service.ParcelProducerService;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.ClassRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.time.Instant;

import static java.util.Collections.singletonList;

@ActiveProfiles("test")
@SpringBootTest
@Import(ConsumerConfigTest.class)
@ContextConfiguration(initializers = KafkaContainers.Initializer.class)
public class ParcelServiceTest {
    @Value("${spring.kafka.topic}")
    private String topic;

    @Autowired
    private ParcelProducerService service;

    @Autowired
    private Consumer<String, Parcel> consumer;

    @BeforeAll
    static void beforeAll() {
        KafkaContainers.kafka.start();
    }

    @Test
    public void sendToKafkaMessageTest() {
        Parcel parcel = getParcel();
        service.sendMessage(parcel);

        ConsumerRecord<String, Parcel> record = KafkaTestUtils.getSingleRecord(consumer, topic);

        Assertions.assertEquals(parcel, record.value());
        consumer.close();
    }

    private Parcel getParcel() {
        return Parcel.builder()
                .timestamp(Instant.now())
                .idReceiver(1000)
                .sender("testsender")
                .build();
    }
}
