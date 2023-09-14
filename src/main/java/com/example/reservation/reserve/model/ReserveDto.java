package com.example.reservation.reserve.model;

import com.example.reservation.reserve.entity.Reserve;
import com.example.reservation.store.entity.Store;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReserveDto {

    private int id;
    private Store store;
    private int numberOfPeople;
    private String phone;
    private String status;
    private LocalDateTime reserveDt;
    private LocalDateTime deadline;

    public static ReserveDto of(Reserve reserve) {
        return ReserveDto.builder()
                .id(reserve.getId())
                .store(reserve.getStore())
                .numberOfPeople(reserve.getNumberOfPeople())
                .phone(reserve.getPhone())
                .status(reserve.getStatus())
                .reserveDt(reserve.getReserveDt())
                .deadline(reserve.getDeadline())
                .build();
    }

}
