package io.codero.sendler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class Scheduler {
    @Autowired
    private Producer producer;

    @Scheduled(cron = "${app.schedule.generation-period}")
    public void generate() {
        producer.sendMessage(new Parcel(1, 1, 1, "Иванов Иван иванович"));
    }

}
