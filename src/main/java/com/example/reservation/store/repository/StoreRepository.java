package com.example.reservation.store.repository;

import com.example.reservation.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Integer> {
    Optional<Store> findByEmailAndNameAndLocation(String email, String name, String location);

    List<Store> getStoreByEmail(String email);

    Optional<Store> findStoreByEmail(String email);

    List<Store> getStoreByOpenYnTrue();

    Optional<Store> getStoreById(int storeId);

    long countByOpenYnIsTrue();

    long countByEmail(String email);

}
