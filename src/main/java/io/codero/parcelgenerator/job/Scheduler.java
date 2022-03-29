package io.codero.parcelgenerator.job;

import io.codero.parcelgenerator.service.ParcelGenerator;
import io.codero.parcelgenerator.service.ParcelProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Profile("!test")
@Service
@RequiredArgsConstructor
public class Scheduler {
    private final ParcelProducerService producer;
    private final ParcelGenerator parcelGenerator;

    @Scheduled(fixedDelayString = "${app.schedule.generation-period}")
    public void generate() {
        producer.sendMessage(parcelGenerator.generate());
    }

}
