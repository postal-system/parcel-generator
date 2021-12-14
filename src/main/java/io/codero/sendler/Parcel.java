package io.codero.sendler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parcel {
    //    id посылки
    private Integer id;
    //    id отделения приёма
    private Integer idPostOfficeDestination;
    //    id получателя
    private Integer idRecipient;
    //    фио отправителя
    private String recipientName;

//    public Parcel(Integer idPostOfficeDestination, Integer idRecipient, String recipientName) {
//        this.idPostOfficeDestination = idPostOfficeDestination;
//        this.idRecipient = idRecipient;
//        this.recipientName = recipientName;
//    }
}
