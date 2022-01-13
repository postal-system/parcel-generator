package io.codero.parcelgenerator.service;

import io.codero.parcelgenerator.enity.Parcel;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ParcelGenerator {

    private final List<String> names = Arrays.asList(
            "Иванов Иван Иванович",
            "Алексеев Алексей Алексеевич",
            "Дмитриев Дмитрий Дмитриевич",
            "Максимов Максим Максимович",
            "Васильев Василий Васильевич",
            "Обоев Рулон Поджогович",
            "Надоев Рекорд Подрывович",
            "Устоев Подрыв Рулонович",
            "Сараев Поджег Рекордович"
    );

    public Parcel generate() {
        return Parcel.builder()
                .id(UUID.randomUUID())
                .idReceiver(getRandomId())
                .postOfficeReceiverId(getRandomId())
                .sender(names.get((int) (Math.random() * names.size())))
                .timestamp(Instant.now())
                .build();
    }

    private int getRandomId() {
        return (int) (Math.random() * (10 - 1)) + 1;
    }
}
