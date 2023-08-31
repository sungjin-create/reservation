package com.example.reservation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TestController {

    @GetMapping("/member/test")
    public ModelAndView testPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/test");
        return modelAndView;
    }
}
