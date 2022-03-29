package io.codero.parcelgenerator.enity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Parcel {
    private UUID id;
    private Integer postOfficeId;
    private Integer idReceiver;
    private String sender;
    private Instant timestamp;
}
