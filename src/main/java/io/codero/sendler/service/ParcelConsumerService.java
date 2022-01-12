package io.codero.sendler.service;

import io.codero.sendler.enity.Parcel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Slf4j
@Data
@Component
public class ParcelConsumerService {
    private CountDownLatch latch = new CountDownLatch(1);
    private Parcel payload;

    @KafkaListener(topics = "parcel", groupId = "group_id", containerFactory = "kafkaListenerContainerFactory" )
    public void consume(Parcel parcel) {
        log.info("#### -> Consumed message -> {}", parcel);
        setPayload(parcel);
        latch.countDown();
    }
}