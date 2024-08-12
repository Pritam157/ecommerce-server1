package com.patilluxuries.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.patilluxuries.exception.ProductException;
import com.patilluxuries.model.Product;
import com.patilluxuries.model.Review;
import com.patilluxuries.model.User;
import com.patilluxuries.repository.ProductRepository;
import com.patilluxuries.repository.ReviewRepository;
import com.patilluxuries.request.ReviewRequest;

public class ReviewServiceImplementation implements ReviewService{

	
	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductRepository productRepository;
	
	
	
	@Override
	public Review createReview(ReviewRequest req, User user) throws ProductException {
		
		Product product=productService.findProductById(req.getProdcutId());
		
		Review review=new Review();
		
		review.setUser(user);
		review.setProduct(product);
		review.setReview(req.getReview());
		review.setCreatedAt(LocalDateTime.now());
		
		
		return reviewRepository.save(review);
	}

	@Override
	public List<Review> getAllReview(Long productId) {
		
		return reviewRepository.getAllProductsReview(productId);
	}

	
	
}
