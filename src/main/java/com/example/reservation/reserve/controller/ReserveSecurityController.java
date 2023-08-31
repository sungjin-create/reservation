package com.example.reservation.reserve.controller;

import com.example.reservation.member.controller.BaseController;
import com.example.reservation.reserve.entity.Reserve;
import com.example.reservation.reserve.model.ReservationProcess;
import com.example.reservation.reserve.model.ReserveDto;
import com.example.reservation.reserve.model.ReserveModel;
import com.example.reservation.reserve.service.ReserveService;
import com.example.reservation.store.entity.Store;
import com.example.reservation.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReserveSecurityController extends BaseController {

    private final ReserveService reserveService;
    private final StoreService storeService;
    /**
     * 일반회원
     * 예약페이지 보기
     */
    @GetMapping("/member/reserve/store")
    public ModelAndView reserveStorePage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/member/reservation");
        modelAndView.addObject("storeId", request.getParameter("storeId"));
        return modelAndView;
    }

    /**
     * 일반회원
     * 예약정보 받아서 저장한후, '내 예약 현황' 페이지로 이동
     */
    @PostMapping("/member/reserve/store")
    public ModelAndView reserveStoreApi(Authentication authentication, @ModelAttribute ReserveModel reserveModel) {
        //사용자 찾기
        String email = getUserEmail(authentication);
        reserveService.addReservation(email, reserveModel);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/member/reserve/store/my");
        return modelAndView;
    }

    /**
     * 일반 회원
     * 내 예약 현황 페이지 보기
     */
    @GetMapping("/member/reserve/store/my")
    public ModelAndView reserveStorePage(Authentication authentication) {
        //사용자 찾기
        String email = getUserEmail(authentication);
        List<Reserve> reserveList = reserveService.getMyReservation(email);
        boolean result = true;
        if (reserveList.isEmpty()) {
            result = false;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/member/my-reserve-page");
        modelAndView.addObject("reserveList", reserveList);
        modelAndView.addObject("result", result);
        return modelAndView;
    }

    private static String getUserEmail(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    /*
     *매니저
     *예약현황 보기
     *현재 로그인한 이메일로 사용자 확인, 사용자의 전화번호로 예약 정보 확인
     */
    @GetMapping("/manager/reserve/page")
    public ModelAndView getManagerReservePage(Authentication authentication) {
        String email = getUserEmail(authentication);
        List<Store> storeList = storeService.getStore(email);
        List<ReserveDto> reservationList = reserveService.getReservationByStore(storeList);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/manager/reservation-list");
        modelAndView.addObject("reservationList", reservationList);
        return modelAndView;
    }
    /*
    예약을 수락 혹은 거절
    reserveId= 예약id
    request= 수락/거절 확인
     */

    @PostMapping("/manager/reserve/process")
    public ModelAndView processReservation(@ModelAttribute ReservationProcess reservationProcess) {

        reserveService.processReservation(reservationProcess);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/manager/reserve/page");
        return modelAndView;
    }


    @GetMapping("/manager/reserve/history")
    public ModelAndView getReserveHistoryPage(Authentication authentication, HttpServletRequest request) {
        String email = getUserEmail(authentication);
        List<Store> storeList = storeService.getStore(email);
        List<ReserveDto> reserveList = reserveService.getReserveHistory(storeList);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/manager/reserve-history");
        modelAndView.addObject("reserveList", reserveList);
        return modelAndView;
    }
}
