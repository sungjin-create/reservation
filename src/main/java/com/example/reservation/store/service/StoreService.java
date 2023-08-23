package com.example.reservation.store.service;

import com.example.reservation.store.entity.Store;
import com.example.reservation.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public boolean addStore(String email, String name, String location, String description) {

        Optional<Store> opStore = storeRepository.findByEmailAndNameAndLocation(email, name, location);

        if (opStore.isPresent()) {
            return false;
        }

        storeRepository.save(Store.builder()
                .email(email)
                .name(name)
                .location(location)
                .description(description)
                .build());
        return true;
    }

    public List<Store> getStoreList(String email) {

        return storeRepository.getStoreByEmail(email);
    }
}
