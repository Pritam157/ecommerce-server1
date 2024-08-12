package com.patilluxuries.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patilluxuries.exception.ProductException;
import com.patilluxuries.model.Cart;
import com.patilluxuries.model.CartItem;
import com.patilluxuries.model.Product;
import com.patilluxuries.model.User;
import com.patilluxuries.repository.CartRepository;
import com.patilluxuries.request.AddItemRequest;

@Service
public class CartServiceImplimentation implements CartService{

	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private ProductService productService;
	
	
	
	
	@Override
	public Cart createCart(User user) {
		
		Cart cart=new Cart();
		
		cart.setUser(user);
			
		return cartRepository.save(cart);
	}

	@Override
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
		
		Cart cart=cartRepository.findByUserId(userId);
		
		Product product=productService.findProductById(req.getProductId());
		
		CartItem isPresent=cartItemService.isCartItemExist(cart, product, req.getSize(), userId);
		
		if(isPresent==null) {
			CartItem cartItem=new CartItem();
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItem.setQuantity(req.getQuantity());
			cartItem.setUserId(userId);
			cartItem.setPrice(req.getQuantity()*product.getDiscountedPrice());
			cartItem.setSize(req.getSize());
			
			CartItem createdCartItem=cartItemService.createCartItem(cartItem);
			cart.getCartItems().add(createdCartItem);
			
			
		}
		
		return "Item Add To Cart";
	}

	@Override
	public Cart finduserCart(Long userId) {
		
		Cart cart=cartRepository.findByUserId(userId);
		
		int totalPrice=0;
		int totalDiscountedPrice=0;
		
		int totalItem=0;
		
		
		
		for(CartItem cartItem :cart.getCartItems()) {
			totalPrice = totalPrice+cartItem.getPrice();
			
			totalDiscountedPrice += cartItem.getDiscountedPrice();
			
			totalItem += cartItem.getQuantity();
			
			
		}
		
		cart.setTotalDiscountedPrice(totalDiscountedPrice);
		cart.setTotalItem(totalItem);
		cart.setTotalPrice(totalPrice);
		cart.setDiscount(totalPrice-totalDiscountedPrice);
		
		
		return cartRepository.save(cart);
	}
	
	

}
