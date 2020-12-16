package com.ecommerce.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Product;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

	public CartItem findByProduct(Product product);
	
	@Transactional
	@Modifying
	@Query("Delete from CartItem where cartItemId=?1")
	public void deleteById(int id);
	
	
	public List<CartItem> findByCart(Cart cart);
}
