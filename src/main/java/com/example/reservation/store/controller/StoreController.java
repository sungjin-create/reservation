package com.example.reservation.store.controller;

import com.example.reservation.store.model.StoreModel;
import com.example.reservation.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    //상점 삭제 api
    @PostMapping("/manager/store/delete")
    public ModelAndView deleteStore(HttpServletRequest request) {
        String id = request.getParameter("id");
        storeService.deleteStoreById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/manager/store/list");
        return modelAndView;
    }

    //상점 수정 페이지
    @PostMapping("/manager/store/update/page")
    public ModelAndView updateStore(@ModelAttribute StoreModel updateStore) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("updateStore", updateStore);
        modelAndView.setViewName("/manager/update-store");
        return modelAndView;
    }

    //상점 정보 수정 api
    @PostMapping("/manager/store/update/api")
    public ModelAndView updateStoreApi(@ModelAttribute StoreModel updateStore) {
        storeService.updateStore(updateStore);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/manager/store/list");
        return modelAndView;
    }
}
