package com.example.reservation.store.service;

import com.example.reservation.exception.UnExistStoreException;
import com.example.reservation.store.entity.Store;
import com.example.reservation.store.mapper.StoreMapper;
import com.example.reservation.store.model.StoreDto;
import com.example.reservation.store.model.StoreManagerParam;
import com.example.reservation.store.model.StoreModel;
import com.example.reservation.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    public boolean addStore(String email, StoreModel storeModel) {

        Optional<Store> opStore = storeRepository.findByEmailAndNameAndLocation(
                email, storeModel.getName(), storeModel.getLocation());

        if (opStore.isPresent()) {
            return false;
        }

        storeRepository.save(Store.builder()
                .email(email)
                .name(storeModel.getName())
                .location(storeModel.getLocation())
                .description(storeModel.getDescription())
                .openYn(storeModel.isOpenYn())
                .regDt(LocalDateTime.now())
                .latitude(storeModel.getLatitude())
                .longitude(storeModel.getLongitude())
                .build());
        return true;
    }

    public List<StoreDto> getStoreList(String email, StoreManagerParam storeManagerParam) {
        storeManagerParam.init();
        storeManagerParam.setEmail(email);
        return storeMapper.storeListManager(storeManagerParam);
    }

    public List<Store> getStore(String email) {
        return storeRepository.getStoreByEmail(email);
    }

    public void deleteStoreById(String stringId) {
        int id = Integer.parseInt(stringId);
        storeRepository.deleteById(id);
    }

    public Store findStoreByEmail(String email) {
        Optional<Store> opStore = storeRepository.findStoreByEmail(email);
        if (opStore.isEmpty()) {
            throw new RuntimeException();
        }
        return opStore.get();
    }

    public void updateStore(StoreModel updateStore) {
        int id = updateStore.getId();
        Optional<Store> opStore = storeRepository.findById(id);
        if (opStore.isEmpty()) {
            throw new UnExistStoreException();
        }

        Store store = opStore.get();
        store.setName(updateStore.getName());
        store.setLocation(updateStore.getLocation());
        store.setDescription(updateStore.getDescription());
        store.setOpenYn(updateStore.isOpenYn());

        storeRepository.save(store);
    }

    public Store findById(int id) {
        Optional<Store> opStore = storeRepository.findById(id);
        if (opStore.isEmpty()) {
            throw new RuntimeException();
        }
        return opStore.get();
    }

    public long getTotalStore(String email) {

        return storeRepository.countByEmail(email);
    }
}
