package io.codero.sendler.service;

import io.codero.sendler.enity.Parcel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ParcelProducerService {
    private final KafkaTemplate<String, Parcel> template;

    @Value("${app.kafka.topic}")
    private String topic;

    public void sendMessage(Parcel parcel) {
        log.info("#### <- Producing message <- {}", parcel);
        this.template.send(topic, parcel);
    }
}