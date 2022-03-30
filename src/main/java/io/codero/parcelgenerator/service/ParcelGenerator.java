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
            "Ломоносов Михаил Васильевич",
            "Павлов Иван Петрович",
            "Пирогов Николай Иванович",
            "Боткин Сергей Петрович",
            "Менделеев Дмитрий Иванович",
            "Кюри Мария Всеволодовна",
            "Попов Александр Степанович",
            "Мечников Илья Ильич",
            "Сахаров Андрей Дмитриевич",
            "Перельман Григорий Яковлевич"
    );

    public Parcel generate() {
        return Parcel.builder()
                .id(UUID.randomUUID())
                .idReceiver(2) /* this value  equals userId from ESK- system  User service */
                .postOfficeId(getRandomId())
                .sender(names.get((int) (Math.random() * names.size())))
                .timestamp(Instant.now())
                .build();
    }

    private int getRandomId() {
        return (int) (Math.random() * (10 - 1)) + 1;
    }
}
