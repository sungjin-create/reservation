package com.example.reservation.member.service;

import com.example.reservation.exception.UnExistStoreException;
import com.example.reservation.member.entity.Member;
import com.example.reservation.exception.UserNotFoundException;
import com.example.reservation.member.model.JoinMemberInput;
import com.example.reservation.member.repository.MemberRepository;
import com.example.reservation.store.entity.Store;
import com.example.reservation.store.mapper.StoreMapper;
import com.example.reservation.store.model.StoreDto;
import com.example.reservation.store.model.StoreModel;
import com.example.reservation.store.model.StoreParam;
import com.example.reservation.store.repository.StoreRepository;
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
    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    //회원 등록하기
    public boolean register(JoinMemberInput parameter) {
        int exist = memberRepository.countByEmailOrPhone(parameter.getEmail(), parameter.getPhone());

        if (exist != 0) {
            return false;
        }


        String encPassword = BCrypt.hashpw(parameter.getPassword(), BCrypt.gensalt());

        Member member = Member.builder()
                .email(parameter.getEmail())
                .name(parameter.getUserName())
                .phone(parameter.getPhone())
                .password(encPassword)
                .registerDate(LocalDateTime.now())
                .managerYn(parameter.isManagerYn())
                .build();
        memberRepository.save(member);

        return true;

    }

    //로그인 관련 메서드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> optionalMember = memberRepository.findByEmail(username);

        if (optionalMember.isEmpty()) {
            throw new UserNotFoundException();
        }

        Member member = optionalMember.get();

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));

        if (member.isManagerYn()) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
        }

        return new User(member.getEmail(), member.getPassword(), grantedAuthorities);
    }

    //정렬값에 따라 리스트 가져오기
    public List<StoreDto> getStoreList(StoreParam storeParam) {

        String order = "";
        String orderAttribute = "";
        if (storeParam.getOrder() == null) {
            order = "name";
            orderAttribute = "asc";
        } else if (storeParam.getOrder().equals("distance")) {
            order = "distance";
            orderAttribute = "asc";
        } else if (storeParam.getOrder().equals("grade")) {
            order = "grade";
            orderAttribute = "desc";
        }else{
            order = "name";
            orderAttribute = "asc";
        }
        storeParam.setOrder(order);
        storeParam.setOrderAttribute(orderAttribute);
        return storeMapper.selectList(storeParam);
    }

    //오픈된 상점 총개수 가져오기
    public long getTotalStore() {
        return storeRepository.countByOpenYnIsTrue();
    }

    //스토어 아이디로 스토어 가져오기
    public StoreModel getStore(int storeId) {
        Optional<Store> optionalStore = storeRepository.getStoreById(storeId);
        if (optionalStore.isEmpty()) {
            throw new UnExistStoreException();
        }
        return StoreModel.of(optionalStore.get());
    }

    //위치 정보 업데이트
    public void updateLocation(String email, double myLatitude, double myLongitude) {

        Member member = memberRepository.findByEmail(email).orElseThrow();
        member.setLatitude(myLatitude);
        member.setLongitude(myLongitude);
        memberRepository.save(member);

    }
}
