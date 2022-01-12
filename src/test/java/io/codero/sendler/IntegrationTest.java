package io.codero.sendler;

import io.codero.sendler.enity.Parcel;
import io.codero.sendler.service.ParcelConsumerService;
import io.codero.sendler.service.ParcelProducerService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class IntegrationTest {
    @ClassRule
    public static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"));

    @Autowired
    private ParcelProducerService producer;

    @Autowired
    private ParcelConsumerService consumer;

    //    @Configuration
    public class KafkaTestContainersConfiguration {
        @Bean
        public ProducerFactory<String, String> producerFactory() {
            Map<String, Object> configProps = new HashMap<>();
            configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
            // more standard configuration
            return new DefaultKafkaProducerFactory<>(configProps);
        }

        @Bean
        public Map<String, Object> consumerConfigs() {
            Map<String, Object> props = new HashMap<>();
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
            props.put(ConsumerConfig.GROUP_ID_CONFIG, "baeldung");
            // more standard configuration
            return props;
        }
    }

    @Test
    public void shouldReturnThatWasPut() throws InterruptedException {
        UUID id = UUID.randomUUID();
        Parcel parcel = new Parcel(id, 1, 1, "ftwmn");
        producer.sendMessage(parcel);

        consumer.getLatch().await(10000, TimeUnit.MILLISECONDS);

        Parcel expected = new Parcel(id, 1, 1, "ftwmn");
        Parcel actual = consumer.getPayload();

        assertThat(consumer.getLatch().getCount(), equalTo(0L));
        Assert.assertEquals(expected, actual);
    }
}
