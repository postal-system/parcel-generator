package io.codero.sendler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;


@Slf4j
@Service
public class Consumer {
    @Autowired
    private KafkaListenerContainerFactory<MessageListenerContainer> ListenerFactory;

    private CountDownLatch latch = new CountDownLatch(1);
    //
    private Parcel payload;


    @KafkaListener(topics = "parcel", groupId = "group_id")
    public void consume(Parcel parcel) {
        log.info(String.format("#### -> Consumed message -> %s", parcel.toString()));
        setPayload(parcel);
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    public Parcel getPayload() {
        return payload;
    }

    public void setPayload(Parcel payload) {
        this.payload = payload;
    }
}