package com.patilluxuries.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.patilluxuries.model.Order;





public interface OrderRerpository extends JpaRepository<Order, Long>{
    
	@Query("SELECT o FROM Order o WHERE o.user.id= :userId AND (o.orderStatus = 'PLACED' OR o.orderStatus= 'CONFIRMED' OR o.orderStatus= 'SHIPPED' OR o.orderStatus= 'DELIVERED')")
	public List<Order> getUsersOrders(@Param("userId")Long userId);
	
	@Query("SELECT o FROM Order o WHERE o.user.id= :userId AND o.orderStatus = :orderStatus ")
	public List<Order> getOrderByorderStatus(@Param("orderStatus") String orderStatus,@Param("userId") Long userId);
}

//o.orderStatus='PENDING' OR