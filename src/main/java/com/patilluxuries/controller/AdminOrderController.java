package com.patilluxuries.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.patilluxuries.exception.Orderexception;
import com.patilluxuries.model.Order;
import com.patilluxuries.responce.ApiResponse;
import com.patilluxuries.service.OrderService;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/")
	public ResponseEntity<List<Order>> getAllOrdersHandler() {
		List<Order> orders=orderService.getAllOrders();
		
		return new ResponseEntity<List<Order>>(orders,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("{orderId}/confirmed")
	public ResponseEntity<Order> ConfromOrderhandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt)throws Orderexception {
		
		Order order=orderService.confirmedOrder(orderId);
		
		return  new ResponseEntity<>(order,HttpStatus.OK);
	}
	
	@PutMapping("{orderId}/ship")
	public ResponseEntity<Order> ShippedOrderHandler(@PathVariable Long orderId,@RequestHeader("Authorization") String jwt)throws Orderexception {
		
		Order order=orderService.shippedOrder(orderId);
		
		return new ResponseEntity<>(order,HttpStatus.OK);
	}
	
	@PutMapping("{orderId}/deliver")
	public ResponseEntity<Order> DeliverOrderHandler(@PathVariable Long orderId,@RequestHeader("Authorization") String jwt)throws Orderexception {
		
		
		Order order=orderService.deliveredOrder(orderId);
		
		
		return new ResponseEntity<>(order,HttpStatus.OK);
	}
	
	
	@PutMapping("{orderId}/cancel")
	public ResponseEntity<Order> CancelOrderHandler(@PathVariable Long orderId,@RequestHeader("Authorization") String jwt)throws Orderexception {
		
		
		Order order=orderService.cancledOrder(orderId);
		
		ApiResponse res=new ApiResponse();
		res.setMessage("order deleted successfully");
		res.setStatus(true);
		
		
		return new ResponseEntity<>(order,HttpStatus.OK);
	}

}
