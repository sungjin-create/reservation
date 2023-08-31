package com.example.reservation.review.service;

import com.example.reservation.exception.UnExistReviewException;
import com.example.reservation.exception.UnExistStoreException;
import com.example.reservation.reserve.entity.Reserve;
import com.example.reservation.reserve.entity.ReserveStatus;
import com.example.reservation.reserve.model.ReserveDto;
import com.example.reservation.reserve.repository.ReserveRepository;
import com.example.reservation.review.entity.Review;
import com.example.reservation.review.model.ReviewInput;
import com.example.reservation.review.model.ReviewModel;
import com.example.reservation.review.repository.ReviewRepository;
import com.example.reservation.store.entity.Store;
import com.example.reservation.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReserveRepository reserveRepository;
    private final StoreRepository storeRepository;

    public List<ReviewModel> getReview(int storeId) {
        List<Review> reviewList = reviewRepository.findReviewByStoreId(storeId);
        List<ReviewModel> reviewModelList = new ArrayList<>();
        for (Review review : reviewList) {
            reviewModelList.add(ReviewModel.of(review));
        }
        return reviewModelList;
    }

    public ReviewModel getMyReview(String email, int storeId) {
        Optional<Review> optionalReview = reviewRepository.findReviewByEmailAndStoreId(email, storeId);
        return optionalReview.map(ReviewModel::of).orElse(null);
    }

    public List<ReserveDto> canWriteReviewReserveList(String email) {

        List<Reserve> reserveList = reserveRepository.findReserveByEmailAndStatus(email, ReserveStatus.RESERVE_STATUS_ACCEPT);
        List<ReserveDto>  reserveDtoList = new ArrayList<>();

        //리뷰는 한개만 쓸수 있도록 한다.
        //리뷰가 없는 가게의 예약만 가져오기
        for (Reserve reserve : reserveList) {
            if (reviewRepository.countReviewByEmailAndStoreId(email, reserve.getStore().getId()) == 0) {
                reserveDtoList.add(ReserveDto.of(reserve));
            }
        }

        return reserveDtoList;
    }

    public void addReview(String email, ReviewInput reviewInput) {
        Store store = storeRepository.findById(reviewInput.getStoreId()).orElseThrow(
                UnExistStoreException::new);

        List<Review> reviewList = reviewRepository.findReviewByStoreId(store.getId());
        double totalGrade=0;
        for (Review review : reviewList) {
            totalGrade += review.getGrade();
        }
        totalGrade += reviewInput.getGrade();

        int ReviewCount = store.getCountReview() + 1;
        double grade = totalGrade / ReviewCount;
        store.setCountReview(store.getCountReview() + 1);
        store.setGrade(grade);
        storeRepository.save(store);

        reviewRepository.save(Review.builder()
                .store(store)
                .grade(reviewInput.getGrade())
                .email(email)
                .contents(reviewInput.getContents())
                .build());

    }


}