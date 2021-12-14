package io.codero.sendler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Producer {
    @Autowired
    private KafkaTemplate<String, Parcel> template;

    public void sendMessage(Parcel parcel) {
        log.info(String.format("#### <- Producing message -> %s", parcel.toString()));
        this.template.send("parcel", parcel);
    }
}