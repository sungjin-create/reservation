package com.example.reservation.exception;

public class UnExistStoreException extends RuntimeException{
    public UnExistStoreException() {
        super("존재하지 않는 상점입니다.");
    }
}
