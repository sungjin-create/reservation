package com.example.reservation.review.service;

import com.example.reservation.reserve.repository.ReserveRepository;
import com.example.reservation.review.repository.ReviewRepository;
import com.example.reservation.store.repository.StoreRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private final ReviewRepository reviewRepository;

    @Mock
    private final ReserveRepository reserveRepository;

    @Mock
    private final StoreRepository storeRepository;

    @InjectMocks
    ReviewService reviewService;

    ReviewServiceTest(ReviewRepository reviewRepository, ReserveRepository reserveRepository, StoreRepository storeRepository) {
        this.reviewRepository = reviewRepository;
        this.reserveRepository = reserveRepository;
        this.storeRepository = storeRepository;
    }
}