package com.ecommerce.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecommerce.model.ConfirmedOrder;

@Repository
public interface ConfirmedOrderRepository extends JpaRepository<ConfirmedOrder, Integer> {

	
	@Query("from ConfirmedOrder where isFulfilled = false")
	public List<ConfirmedOrder> getAllPendingOrders();
}
