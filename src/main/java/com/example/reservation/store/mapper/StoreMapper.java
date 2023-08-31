package com.example.reservation.store.mapper;

import com.example.reservation.store.model.StoreDto;
import com.example.reservation.store.model.StoreManagerParam;
import com.example.reservation.store.model.StoreParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StoreMapper {
    List<StoreDto> selectList(StoreParam storeParam);

    List<StoreDto> storeListManager(StoreManagerParam storeManagerParam);
}
