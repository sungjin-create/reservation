package com.example.reservation.store.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String name;
    private String location;
    private String description;
    private boolean openYn;
    private double grade;
    private int countReview;
    private LocalDateTime regDt;
    private double latitude;
    private double longitude;

}
