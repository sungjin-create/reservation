package com.example.reservation.exception;


public class ExistUserException extends RuntimeException {
    public ExistUserException(){
        super("이미 존재하는 핸드폰 번호입니다.");
    }
}
