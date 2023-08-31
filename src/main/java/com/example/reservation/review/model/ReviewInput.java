package com.example.reservation.review.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewInput {
    private String contents;
    private int grade;
    private int reserveId;
    private int storeId;
}
