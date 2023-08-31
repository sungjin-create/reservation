package com.example.reservation.reserve.controller;

import com.example.reservation.reserve.model.ReserveDto;
import com.example.reservation.reserve.service.ReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class KioskController {

    private final ReserveService reserveService;

    //키오스크 페이지
    @GetMapping("/kiosk/reservation")
    public ModelAndView kioskPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/kiosk/main");
        return modelAndView;
    }

    //예약자 핸드폰번호로 확인
    @PostMapping("/kiosk/reservation")
    public ModelAndView getKioskReservationList(HttpServletRequest request) {

        List<ReserveDto> reserveList = reserveService.checkReserve(request);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/kiosk/reserve-list");
        modelAndView.addObject("reserveList", reserveList);
        return modelAndView;
    }

    //예약자 도착 확인 메서드, 10분 초과시 실패
    @PostMapping("/kiosk/reservation/check")
    public ModelAndView kioskReservationCheck(HttpServletRequest request) {
        boolean result = reserveService.arriveCheck(request);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/kiosk/arrive-confirm");
        modelAndView.addObject("result", result);
        return modelAndView;
    }
}
