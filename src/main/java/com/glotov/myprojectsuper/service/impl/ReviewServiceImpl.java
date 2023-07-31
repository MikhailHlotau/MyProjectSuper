package com.glotov.myprojectsuper.service.impl;

import com.glotov.myprojectsuper.model.Review;
import com.glotov.myprojectsuper.repository.ReviewRepository;
import com.glotov.myprojectsuper.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> findAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public List<Review> findReviewsByOrderId(int orderId) {
        return reviewRepository.findByOrderId(orderId);
    }

    @Override
    public Review addReview(Review review) {
        return reviewRepository.save(review);
    }
}
