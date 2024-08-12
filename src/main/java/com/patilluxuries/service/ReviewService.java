package com.patilluxuries.service;

import java.util.List;

import com.patilluxuries.exception.ProductException;
import com.patilluxuries.model.Review;
import com.patilluxuries.model.User;
import com.patilluxuries.request.ReviewRequest;

public interface ReviewService {

	public Review createReview(ReviewRequest reviewRequest,User user)throws ProductException;
	
	public List<Review> getAllReview(Long productId);
}
