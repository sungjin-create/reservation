package com.example.reservation.member.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String email;
    private String phone;
    private String password;
    private LocalDateTime registerDate;
    private double latitude;
    private double longitude;

    private boolean managerYn;

}
