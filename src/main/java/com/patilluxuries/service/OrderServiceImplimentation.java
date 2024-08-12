package com.patilluxuries.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patilluxuries.exception.Orderexception;
import com.patilluxuries.model.Address;
import com.patilluxuries.model.Cart;
import com.patilluxuries.model.CartItem;
import com.patilluxuries.model.Order;
import com.patilluxuries.model.OrderItem;
import com.patilluxuries.model.User;
import com.patilluxuries.repository.AddressRepository;
import com.patilluxuries.repository.CartRepository;
import com.patilluxuries.repository.OrderItemRepository;
import com.patilluxuries.repository.OrderRerpository;
import com.patilluxuries.repository.UserRepository;

@Service
public class OrderServiceImplimentation implements OrderService{

	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private CartService cartService;
	@Autowired
	private ProductService productService;
	@Autowired
	private OrderRerpository orderRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OrderItemService OrderItemService;
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	
	
	@Override
	public Order createOrder(User user, Address shippingAddress) {
		
		shippingAddress.setUser(user);
		Address address=addressRepository.save(shippingAddress);
		user.getAddress().add(address);
		userRepository.save(user);
		
		Cart cart=cartService.finduserCart(user.getId());
		List<OrderItem> orderItems=new ArrayList<>();
		
		for(CartItem item :cart.getCartItems() ) {
			OrderItem orderItem=new OrderItem();
			
			orderItem.setPrice(item.getPrice());
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setSize(item.getSize());
			orderItem.setUserId(item.getUserId());
			orderItem.setDiscountedPrice(item.getDiscountedPrice());
			
			OrderItem createdOrderItem=orderItemRepository.save(orderItem);
			
			orderItems.add(createdOrderItem);
		}
		
		Order createOrder=new Order();
		createOrder.setUser(user);
		createOrder.setOrderItems(orderItems);
		createOrder.setTotalPrice(cart.getTotalPrice());
		createOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
		createOrder.setDiscount(cart.getDiscount());
		createOrder.setTotalItem(cart.getTotalItem());
		createOrder.setShippingAddress(address);
		createOrder.setOrderDate(LocalDateTime.now());
		createOrder.setOrderStatus("PENDING");
		createOrder.getPaymentDetails().setStatus("PENDING");
		createOrder.setCreatedAt(LocalDateTime.now());
		
		Order savedOrder=orderRepository.save(createOrder);
		
		for(OrderItem item:orderItems) {
			item.setOrder(savedOrder);
			orderItemRepository.save(item);
		}
		
		return savedOrder;
	}

	@Override
	public Order findOrderById(Long orderId) throws Orderexception {
		Optional<Order> opt=orderRepository.findById(orderId);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		
		throw new Orderexception("order not exist with id"+orderId);
	}

	@Override
	public List<Order> usersOrderHistory(Long userId) {
		List<Order> orders=orderRepository.getUsersOrders(userId);
		return orders;
	}

	@Override
	public Order placedOrder(Long orderId) throws Orderexception {
		Order order =findOrderById(orderId);
		order.setOrderStatus("PLACED");
		order.getPaymentDetails().setStatus("COMPLETED");
		return orderRepository.save(order);
	}

	@Override
	public Order confirmedOrder(Long orderId) throws Orderexception {
		Order order = findOrderById(orderId);
		order.setOrderStatus("CONFIRMED");
		
		return orderRepository.save(order);
	}

	@Override
	public Order shippedOrder(Long orderId) throws Orderexception {
	
		Order order=findOrderById(orderId);
		order.setOrderStatus("SHIPPED");
		return orderRepository.save(order);
	}

	@Override
	public Order deliveredOrder(Long orderId) throws Orderexception {
		Order order=findOrderById(orderId);
		order.setOrderStatus("DELIVERED");
		return orderRepository.save(order);
	}

	@Override
	public Order cancledOrder(Long orderId) throws Orderexception {
		Order order=findOrderById(orderId);
		order.setOrderStatus("CANCELLED");
		return orderRepository.save(order);
	}

	@Override
	public List<Order> getAllOrders() {
		
		return orderRepository.findAll();
	}

	@Override
	public void deleteOrder(Long orderId) throws Orderexception {
		Order order=findOrderById(orderId);
		
	   orderRepository.deleteById(orderId);
	}

	@Override
	public List<Order> getOrderByorderStatus(String status, Long userId) throws Orderexception {
		
		List<Order> orders=orderRepository.getOrderByorderStatus(status, userId);
		System.out.println(orders);
		
		return orders;
	}
	




	
}
