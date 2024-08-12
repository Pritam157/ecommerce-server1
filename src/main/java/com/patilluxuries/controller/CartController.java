package com.patilluxuries.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patilluxuries.exception.ProductException;
import com.patilluxuries.exception.UserException;
import com.patilluxuries.model.Cart;
import com.patilluxuries.model.User;
import com.patilluxuries.request.AddItemRequest;
import com.patilluxuries.responce.ApiResponse;
import com.patilluxuries.service.CartService;
import com.patilluxuries.service.UserService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization")String jwt)throws UserException{
		
		
		User user=userService.finduserProfileByJwt(jwt);
		Cart cart=cartService.finduserCart(user.getId());
		
		return new ResponseEntity<Cart>(cart,HttpStatus.OK);
		
		
	}
	
	@PutMapping("/add")
	public ResponseEntity<ApiResponse> addItemToCart(@RequestHeader("Authorization")String jwt, @RequestBody AddItemRequest req)throws UserException,ProductException {
		
		User user=userService.finduserProfileByJwt(jwt);
		cartService.addCartItem(user.getId(), req);
		
		ApiResponse res=new ApiResponse("item added to cart",true);
		
		return new ResponseEntity<ApiResponse>(res,HttpStatus.OK);
	}
}
