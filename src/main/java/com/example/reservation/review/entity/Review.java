package com.example.reservation.review.entity;

import com.example.reservation.store.entity.Store;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Store store;
    private String email;
    private double grade;
    private String contents;
    private LocalDateTime regDt;
}
