package com.patilluxuries.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patilluxuries.exception.ProductException;
import com.patilluxuries.exception.UserException;
import com.patilluxuries.model.Rating;
import com.patilluxuries.model.User;
import com.patilluxuries.service.RatingRequest;
import com.patilluxuries.service.RatingService;
import com.patilluxuries.service.UserService;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private RatingService ratingService;
	
	@PostMapping("/create")
	public ResponseEntity<Rating> createRating(@RequestBody RatingRequest req,@RequestHeader("Authorization")String jwt) throws UserException,ProductException{
		
		User user=userService.finduserProfileByJwt(jwt);
		
		Rating rating=ratingService.createReating(req, user);
		
		
		return new ResponseEntity<Rating>(rating,HttpStatus.CREATED);
	}
	
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Rating>> getProductsRating(@PathVariable Long productId,@RequestHeader("Authorization")String jwt) throws UserException,ProductException{
		
		User user=userService.finduserProfileByJwt(jwt);
		
		List<Rating> rating=ratingService.getProductsRating(productId);
		
		
		return new ResponseEntity<List<Rating>>(rating,HttpStatus.ACCEPTED);
	}

}
