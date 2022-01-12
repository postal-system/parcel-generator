package io.codero.sendler.job;

import io.codero.sendler.enity.Parcel;
import io.codero.sendler.service.ParcelProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class Scheduler {
    private final ParcelProducerService producer;

    @Scheduled(cron = "${app.schedule.generation-period}")
    public void generate() {
        producer.sendMessage(new Parcel(UUID.randomUUID(), 644666, 1, "Иванов Иван Иванович"));
    }

}
