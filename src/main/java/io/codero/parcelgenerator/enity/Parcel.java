package io.codero.parcelgenerator.enity;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class Parcel {
    private UUID id;
    private Integer postOfficeReceiverId;
    private Integer idReceiver;
    private String sender;
    private Instant timestamp;
}
