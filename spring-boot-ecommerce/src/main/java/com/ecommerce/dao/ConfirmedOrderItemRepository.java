package com.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.model.ConfirmedOrderItem;

@Repository
public interface ConfirmedOrderItemRepository extends JpaRepository<ConfirmedOrderItem, Integer>{

}
