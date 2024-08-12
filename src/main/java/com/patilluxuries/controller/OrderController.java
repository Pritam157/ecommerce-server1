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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.patilluxuries.exception.Orderexception;
import com.patilluxuries.exception.UserException;
import com.patilluxuries.model.Address;
import com.patilluxuries.model.Order;
import com.patilluxuries.model.User;
import com.patilluxuries.service.OrderService;
import com.patilluxuries.service.UserService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress,@RequestHeader("Authorization")String jwt) throws UserException{
		
		User user=userService.finduserProfileByJwt(jwt);
		
		Order order=orderService.createOrder(user, shippingAddress);
		
	    System.out.println("Order"+order);
	    
		return new ResponseEntity<>(order,HttpStatus.CREATED);
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<Order>> usersOrderHistory(@RequestHeader("Authorization")String jwt)throws UserException {
		
		User user=userService.finduserProfileByJwt(jwt);
		
		List<Order> orders=orderService.usersOrderHistory(user.getId());
		
		return  new ResponseEntity<>(orders,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Order>  findOrderById(@PathVariable Long id,@RequestHeader("Authorization")String jwt)throws UserException,Orderexception {
		
		User user=userService.finduserProfileByJwt(jwt);
		
		Order order=orderService.findOrderById(id);
		
		
		return new ResponseEntity<Order>(order,HttpStatus.ACCEPTED);
	}
	
	
	@GetMapping("/{orderId}/placed")
	public ResponseEntity<Order> orderPlaced(@RequestHeader("Authorization")String jwt,@PathVariable Long orderId)throws UserException,Orderexception {
		
		User user=userService.finduserProfileByJwt(jwt);
		
		Order order=orderService.placedOrder(orderId);
		
		return new ResponseEntity<Order>(order,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/orderstatus/{orderStatus}")
	public ResponseEntity<List<Order>> getOrderByOrderStatus(@PathVariable("orderStatus") String orderStatus,@RequestHeader("Authorization")String jwt)throws UserException,Orderexception  {
		
		User user=userService.finduserProfileByJwt(jwt);
		orderStatus=orderStatus.toUpperCase();
		List<Order> orders=orderService.getOrderByorderStatus(orderStatus.toUpperCase(),user.getId());
//		System.out.println(orders);
		
		return new ResponseEntity<List<Order>>(orders,HttpStatus.OK);
	}
	
	
//	@GetMapping("/getAllOrders")
//	public ResponseEntity<List<Order>> getAllOrders(){
//		
//		List<Order> orders=orderService.getAllOrders();
//		
//		return  new ResponseEntity<>(orders,HttpStatus.OK);
//		
//	}
	
	
	
	
	
}
