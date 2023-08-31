package com.example.reservation.review.model;

import com.example.reservation.review.entity.Review;
import com.example.reservation.store.entity.Store;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewModel {

    private int id;
    private Store store;
    private double grade;
    private String contents;

    public static ReviewModel of(Review review) {
        return ReviewModel.builder()
                .store(review.getStore())
                .grade(review.getGrade())
                .contents(review.getContents())
                .build();
    }

}
