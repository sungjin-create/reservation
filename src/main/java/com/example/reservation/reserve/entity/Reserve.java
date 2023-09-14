package com.example.reservation.reserve.entity;

import com.example.reservation.store.entity.Store;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Reserve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Store store;
    private int numberOfPeople;

    private String email;
    private String phone;
    private String status;
    private LocalDateTime reserveDt;
    private LocalDateTime deadline;
    private String arriveCheck;

}
