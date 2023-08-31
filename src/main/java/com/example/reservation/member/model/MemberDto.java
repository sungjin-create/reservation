package com.example.reservation.member.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
    int id;
    String email;
    boolean managerYn;
    String name;
    String password;
    String phone;
    LocalDateTime registerDate;
}
