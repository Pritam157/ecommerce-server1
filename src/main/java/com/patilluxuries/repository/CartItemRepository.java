package com.patilluxuries.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.patilluxuries.model.Cart;
import com.patilluxuries.model.CartItem;
import com.patilluxuries.model.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

	@Query("SELECT ci FROM CartItem ci Where ci.cart= :cart AND ci.product= :product And ci.size= :size AND ci.userId= :userId")
	public CartItem isCartItemExust(@Param("cart") Cart cart,@Param("product")Product product,@Param("size")String size,@Param("userId")Long userId);
	
}
