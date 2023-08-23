package com.example.reservation.store.controller;

import com.example.reservation.store.entity.Store;
import com.example.reservation.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SecurityController {

    private final StoreService storeService;


    @GetMapping("/manager/store/register")
    public ModelAndView getStoreListPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/manager/register-store");
        return modelAndView;
    }

    @PostMapping("/manager/store/register")
    public ModelAndView currentUserName(Authentication authentication, HttpServletRequest request) {
        String email = getUserEmail(authentication);
        String name = request.getParameter("name");
        String location = request.getParameter("location");
        String description = request.getParameter("description");

        boolean result = storeService.addStore(email, name, location, description);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/manager/register-store-result");
        modelAndView.addObject("result", result);
        return modelAndView;
    }

    private static String getUserEmail(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }


    @GetMapping("/manager/store/list")
    public ModelAndView storeListPage(Authentication authentication) {
        String email = getUserEmail(authentication);
        List<Store> storeList = storeService.getStoreList(email);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/manager/store-list");
        modelAndView.addObject("storeList", storeList);

        return modelAndView;
    }


}
