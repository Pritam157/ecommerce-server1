package com.patilluxuries.service;

import com.patilluxuries.exception.ProductException;
import com.patilluxuries.model.Cart;
import com.patilluxuries.model.User;
import com.patilluxuries.request.AddItemRequest;

public interface CartService {


	public Cart createCart(User user);
	
	public String addCartItem(Long userId,AddItemRequest req)throws ProductException;
	
	public Cart finduserCart(Long userId);
	
	
}
