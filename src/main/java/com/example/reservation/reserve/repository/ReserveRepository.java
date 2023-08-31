package com.example.reservation.reserve.repository;

import com.example.reservation.reserve.entity.Reserve;
import com.example.reservation.reserve.model.ReserveDto;
import com.example.reservation.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve, Integer> {
    List<Reserve> getReserveByPhone(String phone);

    List<Reserve> getReserveByStoreAndStatus(Store Store, String status);

    List<Reserve> findReserveByPhoneAndStatusAndArriveCheck(String phone, String Status, String ArriveCheck);

    List<Reserve> findReserveByEmailAndStatus(String email, String status);

    List<Reserve> findReserveByStoreId(int storeId);

}
