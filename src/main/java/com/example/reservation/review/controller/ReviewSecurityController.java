package com.example.reservation.review.controller;

import com.example.reservation.reserve.model.ReserveDto;
import com.example.reservation.review.model.ReviewInput;
import com.example.reservation.review.model.ReviewModel;
import com.example.reservation.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewSecurityController {

    private final ReviewService reviewService;

    @GetMapping("/member/review/page")
    public ModelAndView getReviewPage(Authentication authentication) {

        String email = getUserEmail(authentication);
        //리뷰작성이 가능한 예약들 가져오기
        List<ReserveDto> reserveDtoList = reviewService.canWriteReviewReserveList(email);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/member/review-page");
        modelAndView.addObject("reserveList", reserveDtoList);
        return modelAndView;
    }

    private static String getUserEmail(Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }


    @PostMapping("/member/review/write/page")
    public ModelAndView getReviewPage(HttpServletRequest request) {
        int reserveId = Integer.parseInt(request.getParameter("reserveId"));
        int storeId = Integer.parseInt(request.getParameter("storeId"));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/member/review-write");
        modelAndView.addObject("reserveId", reserveId);
        modelAndView.addObject("storeId", storeId);
        return modelAndView;
    }

    @PostMapping("/member/review/write")
    public ModelAndView addReview(Authentication authentication, ReviewInput reviewInput) {
        String email = getUserEmail(authentication);
        reviewService.addReview(email, reviewInput);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/member/review/page");
        return modelAndView;
    }
}
