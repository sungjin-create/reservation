package com.example.reservation.member.service;

import com.example.reservation.member.model.JoinMemberInput;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {
    boolean register(JoinMemberInput parameter);
}
