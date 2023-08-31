package com.example.reservation.exception;

public class UnExistReserveException extends RuntimeException{
    public UnExistReserveException() {
        super("존재하지 않는 예약입니다.");
    }
}
