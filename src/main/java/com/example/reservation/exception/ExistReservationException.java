package com.example.reservation.exception;

public class ExistReservationException extends RuntimeException{
    public ExistReservationException() {
        super("이미 존재하는 예약입니다.");
    }
}
