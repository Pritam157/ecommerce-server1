package com.patilluxuries.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.patilluxuries.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
