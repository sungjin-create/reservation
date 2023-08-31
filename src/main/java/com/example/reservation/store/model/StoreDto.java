package com.example.reservation.store.model;

import lombok.Data;

@Data
public class StoreDto {
    int id;
    String description;
    String email;
    String location;
    String name;
    boolean openYn;
    int countReview;
    double grade;
    double latitude;
    double longitude;
    double distance;

}
