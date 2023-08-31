package com.example.reservation.store.controller;

import com.example.reservation.member.controller.BaseController;
import com.example.reservation.store.model.StoreDto;
import com.example.reservation.store.model.StoreManagerParam;
import com.example.reservation.store.model.StoreModel;
import com.example.reservation.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoreSecurityController extends BaseController {

    private final StoreService storeService;

    //관리자 스토어 등록 페이지
    @GetMapping("/manager/store/register")
    public ModelAndView getStoreListPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/manager/register-store");
        return modelAndView;
    }

    //관리자 스토어 등록 api
    @PostMapping("/manager/store/register")
    public ModelAndView currentUserName(Authentication authentication, @ModelAttribute StoreModel storeModel) {
        String email = getUserEmail(authentication);
        boolean result = storeService.addStore(email, storeModel);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/manager/register-store-result");
        modelAndView.addObject("result", result);
        return modelAndView;
    }

    private static String getUserEmail(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    //관리자 스토어 리스트 페이지
    @GetMapping("/manager/store/list")
    public ModelAndView storeListPage(Authentication authentication, @ModelAttribute StoreManagerParam storeManagerParam) {
        String email = getUserEmail(authentication);
        List<StoreDto> storeList = storeService.getStoreList(email, storeManagerParam);

        long total = storeService.getTotalStore(email);

        String pageHtml = getPaperHtml(total, storeManagerParam.getPageSize(), storeManagerParam.getPageIndex(), "");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/manager/store-list");
        modelAndView.addObject("storeList", storeList);
        modelAndView.addObject("pager", pageHtml);

        return modelAndView;
    }

}