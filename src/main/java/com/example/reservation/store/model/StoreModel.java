package com.example.reservation.store.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreModel {

    private String name;
    private String location;
    private String description;

}
