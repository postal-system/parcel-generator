package io.codero.sendler.enity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parcel {
    private UUID sourceId;
    private Integer postOfficeReceiverId;
    private Integer idReceiver;
    private String sender;
}
