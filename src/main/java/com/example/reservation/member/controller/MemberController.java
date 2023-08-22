package com.example.reservation.member.controller;

import com.example.reservation.errors.ResponseError;
import com.example.reservation.member.model.JoinMemberInput;
import com.example.reservation.member.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberServiceImpl memberService;


    @RequestMapping("/member/login")
    public ModelAndView loginPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/member/login");
        return modelAndView;
    }

    @GetMapping("/member/course")
    public ModelAndView coursePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/member/course");
        return modelAndView;
    }


    @GetMapping("/member/register")
    public ModelAndView joinPage() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/member/register");
        return modelAndView;

    }


    @PostMapping("/member/register")
    public ModelAndView joinMember(JoinMemberInput joinMemberInput) {

        boolean result = memberService.register(joinMemberInput);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/member/register_complete");
        modelAndView.addObject("result", result);

        return modelAndView;
    }

    private static ResponseEntity<List<ResponseError>> getListResponseEntity(Errors errors) {
        List<ResponseError> errorList = new ArrayList<>();
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach((e) -> {
                errorList.add(ResponseError.of((FieldError) e));
            });
            return new ResponseEntity<>(errorList, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
