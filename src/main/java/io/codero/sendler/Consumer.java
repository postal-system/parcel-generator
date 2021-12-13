package io.codero.sendler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class Consumer {
    @Autowired
    private KafkaListenerContainerFactory<MessageListenerContainer> ListenerFactory;

    @KafkaListener(topics = "parcel", groupId = "group_id")
    public void consume(Parcel parcel) {
        log.info(String.format("#### -> Consumed message -> %s", parcel.toString()));
    }
}