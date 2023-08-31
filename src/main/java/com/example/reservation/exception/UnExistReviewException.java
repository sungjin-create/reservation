package com.example.reservation.exception;

public class UnExistReviewException extends RuntimeException{
    public UnExistReviewException() {
        super("존재하지 않는 리뷰입니다.");
    }
}
