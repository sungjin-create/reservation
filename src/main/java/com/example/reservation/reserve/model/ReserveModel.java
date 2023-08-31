package com.example.reservation.reserve.model;

import com.example.reservation.store.model.StoreModel;
import lombok.*;

import java.time.LocalTime;

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
