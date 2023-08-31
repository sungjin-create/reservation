package com.example.reservation.store.model;

import com.example.reservation.member.model.CommonParam;
import lombok.Data;

@Data
public class StoreParam extends CommonParam {
    String order;
    String orderAttribute;
    double myLatitude;
    double myLongitude;
    String email;

}
