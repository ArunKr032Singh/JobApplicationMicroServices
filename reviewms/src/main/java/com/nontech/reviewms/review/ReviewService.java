package com.nontech.reviewms.review;

import java.util.List;

public interface ReviewService {

    List<Review> getAllReviewsByCompanyId(Long companyId);

    boolean addReview(Long companyId, Review review);

    Review getReview(Long reviewId);

    boolean updateReview(Long reviewId, Review review);

    boolean deleteTReview(Long reviewId);
}
