package com.patilluxuries.service;

import java.util.List;

import com.patilluxuries.exception.Orderexception;
import com.patilluxuries.model.Address;
import com.patilluxuries.model.Order;
import com.patilluxuries.model.User;

public interface OrderService{
	
	public Order createOrder(User user,Address shippingAddress);
	
	public Order findOrderById(Long orderID) throws Orderexception;
	
	public List<Order> usersOrderHistory(Long userID);
	
	public Order placedOrder(Long orderId)  throws Orderexception;
	
	public Order confirmedOrder(Long orderId)  throws Orderexception;
	
	public Order shippedOrder(Long orderId)  throws Orderexception;
	
	public Order deliveredOrder(Long orderId)  throws Orderexception;
	
	public Order cancledOrder(Long orderId)  throws Orderexception;
	
	public List<Order> getAllOrders();
	
	public void deleteOrder(Long orderId)  throws Orderexception;
	
	public List<Order> getOrderByorderStatus(String status,Long userId) throws Orderexception;
	

	
}
