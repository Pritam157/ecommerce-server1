package com.patilluxuries.service;

import java.util.List;

import com.patilluxuries.exception.ProductException;
import com.patilluxuries.model.Rating;
import com.patilluxuries.model.User;

public interface RatingService {
	
	public Rating createReating(RatingRequest req,User user)throws ProductException;
	
	public List<Rating> getProductsRating(Long productId);
	
	

}
