package com.example.reservation.reserve.service;

import com.example.reservation.exception.UnExistReserveException;
import com.example.reservation.exception.UnExistStoreException;
import com.example.reservation.exception.UserNotFoundException;
import com.example.reservation.member.entity.Member;
import com.example.reservation.member.repository.MemberRepository;
import com.example.reservation.reserve.entity.Reserve;
import com.example.reservation.reserve.entity.ReserveStatus;
import com.example.reservation.reserve.model.ReservationProcess;
import com.example.reservation.reserve.model.ReserveDto;
import com.example.reservation.reserve.model.ReserveModel;
import com.example.reservation.reserve.repository.ReserveRepository;
import com.example.reservation.store.entity.Store;
import com.example.reservation.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.reservation.reserve.entity.ArriveCheck.*;
import static com.example.reservation.reserve.entity.ReserveStatus.*;

@Service
@RequiredArgsConstructor
public class ReserveService {

    private final ReserveRepository reserveRepository;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    /**
     * 1. 사용자의 휴대폰 번호로 예약하기
     * 2. 예약은 대기상태로 요청한다
     */
    public void addReservation(String email, ReserveModel reserveModel) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        Store store = storeRepository.getStoreById(reserveModel.getStoreId())
                .orElseThrow(UnExistStoreException::new);

        String phone = member.getPhone();
        String status = ReserveStatus.RESERVE_STATUS_REQ;
        LocalTime tmpDt = LocalTime.parse(reserveModel.getReserveDt());
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reserveDt = LocalDateTime.of(
                now.getYear(), now.getMonth(), now.getDayOfMonth(), tmpDt.getHour(), tmpDt.getMinute());
        LocalDateTime deadline = reserveDt.minusMinutes(10);
        reserveRepository.save(Reserve.builder()
                .email(email)
                .store(store)
                .phone(phone)
                .status(status)
                .numberOfPeople(reserveModel.getNumberOfPeople())
                .reserveDt(reserveDt)
                .deadline(deadline)
                .arriveCheck(ARRIVE_YET)
                .build());
    }

    public List<Reserve> getMyReservation(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        return reserveRepository.getReserveByPhone(member.getPhone());
    }

    public List<ReserveDto> getReservationByStore(List<Store> storeList) {
        List<ReserveDto> reserveDtoList = new ArrayList<>();

        for (Store store : storeList) {
            List<Reserve> reserveByStore = reserveRepository.getReserveByStoreAndStatus(store, RESERVE_STATUS_REQ);
            for (Reserve reserve : reserveByStore) {
                reserveDtoList.add(ReserveDto.of(reserve));
            }
        }
        return reserveDtoList;
    }

    public void processReservation(ReservationProcess reservationProcess) {
        Reserve reserve = reserveRepository.findById(
                reservationProcess.getReserveId()).orElseThrow(UnExistStoreException::new);

        if (reservationProcess.getRequest().equals(RESERVE_STATUS_ACCEPT)) {
            reserve.setStatus(RESERVE_STATUS_ACCEPT);
        } else {
            reserve.setStatus(RESERVE_STATUS_REFUSE);
        }
        reserveRepository.save(reserve);

    }


    /*
    예약 목록
    핸드폰번호, 예약상태-수락, 도착확인-YET인 예약 목록 전달
     */
    public List<ReserveDto> checkReserve(HttpServletRequest request) {

        List<Reserve> reserveList = reserveRepository.findReserveByPhoneAndStatusAndArriveCheck(
                request.getParameter("phone"), RESERVE_STATUS_ACCEPT, ARRIVE_YET);
        List<ReserveDto> reserveDtoList = new ArrayList<>();

        for (Reserve reserve : reserveList) {
            reserveDtoList.add(ReserveDto.of(reserve));
        }
        return reserveDtoList;
    }

    public boolean arriveCheck(HttpServletRequest request) {

        Reserve reserve = reserveRepository.findById(Integer.parseInt(request.getParameter("reserveId"))).orElseThrow(
                UnExistReserveException::new
        );
        boolean result = checkReserveTime(reserve);
        if (!result) {
            reserve.setArriveCheck(ARRIVE_LATE);
            reserveRepository.save(reserve);
            return false;
        }
        reserve.setArriveCheck(ARRIVE_CONFIRM);
        reserveRepository.save(reserve);
        return true;
    }

    private boolean checkReserveTime(Reserve reserve) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime deadline = reserve.getDeadline();
        if (now.isAfter(deadline)) {
            return false;
        }
        return true;
    }

    public List<ReserveDto> getReserveHistory(List<Store> storeList) {
        List<ReserveDto> reserveDtoList = new ArrayList<>();
        for (Store store : storeList) {
            List<Reserve> reserveList = reserveRepository.findReserveByStoreId(store.getId());
            for (Reserve reserve : reserveList) {
                reserveDtoList.add(ReserveDto.of(reserve));
            }
        }

        return reserveDtoList;
    }
}
