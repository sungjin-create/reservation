package com.example.reservation.exception;

public class ExistStoreException extends RuntimeException{
    public ExistStoreException() {
        super("이미 존재하는 상점입니다.");
    }
}
