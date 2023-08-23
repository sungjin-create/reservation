package com.example.reservation.member.service;

import com.example.reservation.member.entity.Member;
import com.example.reservation.exception.UserNotFoundException;
import com.example.reservation.member.model.JoinMemberInput;
import com.example.reservation.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    public boolean register(JoinMemberInput parameter) {
        Optional<Member> optionalMember = memberRepository.findByEmail(parameter.getEmail());

        if (optionalMember.isPresent()) {
            return false;
        }

        String encPassword = BCrypt.hashpw(parameter.getPassword(), BCrypt.gensalt());

        boolean isManager = parameter.getIsManager() == 1;

        Member member = Member.builder()
                .email(parameter.getEmail())
                .name(parameter.getUserName())
                .phone(parameter.getPhone())
                .password(encPassword)
                .registerDate(LocalDateTime.now())
                .isManger(isManager)
                .build();
        memberRepository.save(member);

        return true;

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> optionalMember = memberRepository.findByEmail(username);

        if (optionalMember.isEmpty()) {
            throw new UserNotFoundException();
        }

        Member member = optionalMember.get();

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        if (member.isManger()) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
        }

        return new User(member.getEmail(), member.getPassword(), grantedAuthorities);
    }
}
