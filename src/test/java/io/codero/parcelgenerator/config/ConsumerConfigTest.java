package io.codero.parcelgenerator.config;

import io.codero.parcelgenerator.enity.Parcel;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@TestConfiguration
public class ConsumerConfigTest {

    @Value("${spring.kafka.consumer.group-id:}")
    private String groupId;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrap;

    @Bean
    public Map<String, Object> getConsumerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
        return props;
    }

    @Bean
    public Consumer<String, Parcel> getConsumer() {

        return new DefaultKafkaConsumerFactory<>(
                getConsumerProps(),
                new StringDeserializer(),
                new JsonDeserializer<>(Parcel.class)
        ).createConsumer();
    }
}
