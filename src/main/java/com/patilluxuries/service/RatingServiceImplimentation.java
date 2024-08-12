package com.patilluxuries.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patilluxuries.exception.ProductException;
import com.patilluxuries.model.Product;
import com.patilluxuries.model.Rating;
import com.patilluxuries.model.User;
import com.patilluxuries.repository.RatingRepository;

@Service
public class RatingServiceImplimentation implements RatingService{

	@Autowired
	private RatingRepository ratingRepository;
	@Autowired
	private ProductService productService;
	
	@Override
	public Rating createReating(RatingRequest req, User user) throws ProductException {
		
		Product prodcut=productService.findProductById(req.getProductId());
		
		Rating rating =new Rating();
		rating.setProduct(prodcut);
		rating.setUser(user);
		rating.setRating(req.getRating());
		
		rating.setCreatedAt(LocalDateTime.now());
		
		
		
		return ratingRepository.save(rating);
	}

	@Override
	public List<Rating> getProductsRating(Long productId) {
		
		return ratingRepository.getAllProductsRating(productId);
	}

}
