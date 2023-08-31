package com.example.reservation.member.controller;

import com.example.reservation.member.model.JoinMemberInput;
import com.example.reservation.member.repository.MemberRepository;
import com.example.reservation.member.service.MemberServiceImpl;
import com.example.reservation.review.model.ReviewModel;
import com.example.reservation.review.service.ReviewService;
import com.example.reservation.store.model.StoreDto;
import com.example.reservation.store.model.StoreModel;
import com.example.reservation.store.model.StoreParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberSecurityController extends BaseController {

    private final MemberServiceImpl memberService;
    private final ReviewService reviewService;
    private final MemberRepository memberRepository;

    //로그인 페이지
    @RequestMapping("/member/login")
    public ModelAndView loginPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/member/login");
        return modelAndView;
    }

    //회원가입 페이지
    @GetMapping("/member/register")
    public ModelAndView joinPage() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/member/register");
        return modelAndView;

    }

    //회원가입 등록 api
    @PostMapping("/member/register")
    public ModelAndView joinMember(JoinMemberInput joinMemberInput) {

        boolean result = memberService.register(joinMemberInput);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/member/register-complete");
        modelAndView.addObject("result", result);

        return modelAndView;
    }

    //접근 권한이 없을때 페이지 뷰 설정
    @GetMapping("/member/access-denied")
    public ModelAndView showAccessDeniedPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/member/access-denied");
        return modelAndView;
    }

    //상점 리스트 페이지
    @GetMapping("/member/store/list")
    public ModelAndView showStoreListPage(Authentication authentication, @ModelAttribute StoreParam storeParam) {
        String email = getUserEmail(authentication);
        //위치 정보 업데이트
        extracted(storeParam, email);

        storeParam.init();

        //파라미터를 사용해 storeDto 받아오기
        List<StoreDto> storeList = memberService.getStoreList(storeParam);

        //총 개수 가져오기
        long totalCount = memberService.getTotalStore();
        ModelAndView modelAndView = new ModelAndView();

        //정렬 값 입력
        String order = setOrder(storeParam, modelAndView);

        //페이징 처리
        String pageHtml = getPaperHtml(
                totalCount, storeParam.getPageSize(), storeParam.getPageIndex(), order);

        modelAndView.setViewName("/member/store-list");
        modelAndView.addObject("storeList", storeList);
        modelAndView.addObject("pager", pageHtml);

        return modelAndView;
    }

    //정렬 순서 디폴트 값, 나머지 값 정리 메서드
    private static String setOrder(StoreParam storeParam, ModelAndView modelAndView) {
        String order = "";
        if (storeParam.getOrder() != null) {
            order = storeParam.getOrder();
        }else{
            order = "default";
        }
        modelAndView.addObject("order", order);
        return order;
    }

    //위치 정보 업데이트 메서드
    private void extracted(StoreParam storeParam, String email) {
        double myLatitude = 0;
        double myLongitude = 0;

        if (storeParam.getMyLongitude() != 0) {
            myLatitude = storeParam.getMyLatitude();
            myLongitude = storeParam.getMyLongitude();
        }else{
            myLatitude = memberRepository.findByEmail(email).orElseThrow(/*
            에러페이지 만들기
            */).getLatitude();
            myLongitude = memberRepository.findByEmail(email).orElseThrow().getLongitude();
        }

        //내 위치 정보 업데이트
        memberService.updateLocation(email, myLatitude, myLongitude);

        storeParam.setMyLatitude(myLatitude);
        storeParam.setMyLongitude(myLongitude);
    }

    //상점 디테일 정보 가져오기
    @GetMapping("/member/detail/store/{storeId}")
    public ModelAndView showStoreDetailPage(Authentication authentication, @PathVariable int storeId) {

        //상점정보 가져오기
        StoreModel storeModel = memberService.getStore(storeId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/member/detail-store");
        modelAndView.addObject("storeModel", storeModel);

        //리뷰정보 가져오기
        List<ReviewModel> reviewList = reviewService.getReview(storeId);
        modelAndView.addObject("reviewList", reviewList);

        //내 리뷰 정보 가져오기
        String email = getUserEmail(authentication);
        ReviewModel myReview = reviewService.getMyReview(email, storeId);
        boolean myReviewExist = true;
        if (myReview == null) {
            myReviewExist = false;
        }
        modelAndView.addObject("myReview", myReview);
        modelAndView.addObject("myReviewExist", myReviewExist);
        return modelAndView;
    }

    //이메일 정보 가져오기
    private static String getUserEmail(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

}
