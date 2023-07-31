package com.glotov.myprojectsuper.service;

import com.glotov.myprojectsuper.model.Review;

import java.util.List;

public interface ReviewService {
    List<Review> findAllReviews();

    List<Review> findReviewsByOrderId(int orderId);

    Review addReview(Review review);
}