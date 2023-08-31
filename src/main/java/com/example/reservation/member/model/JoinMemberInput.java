package com.example.reservation.member.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinMemberInput {

    private String userName;
    private String email;
    private String phone;
    private String password;
    private boolean managerYn;

}
