package com.example.reservation.reserve.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReserveModel {
    private int numberOfPeople;
    private String phone;
    private String reserveDt;
    private int storeId;
}
