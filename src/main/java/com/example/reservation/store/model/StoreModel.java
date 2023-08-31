package com.example.reservation.store.model;

import com.example.reservation.store.entity.Store;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreModel {

    private int id;
    private String name;
    private String location;
    private String description;
    private boolean openYn;
    private double grade;
    private int countReview;
    private double latitude;
    private double longitude;

    public static StoreModel of(Store store) {
        return StoreModel.builder()
                .id(store.getId())
                .name(store.getName())
                .location(store.getLocation())
                .description(store.getDescription())
                .openYn(store.isOpenYn())
                .grade(store.getGrade())
                .countReview(store.getCountReview())
                .latitude(store.getLatitude())
                .longitude(store.getLongitude())
                .build();
    }

}
