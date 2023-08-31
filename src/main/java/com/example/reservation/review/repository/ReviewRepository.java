package com.example.reservation.review.repository;

import com.example.reservation.review.entity.Review;
import com.example.reservation.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findReviewByStoreId(int storeId);

    Optional<Review> findReviewByEmailAndStoreId(String email, int storeId);

    int countReviewByEmailAndStoreId(String email, int storeId);

}
