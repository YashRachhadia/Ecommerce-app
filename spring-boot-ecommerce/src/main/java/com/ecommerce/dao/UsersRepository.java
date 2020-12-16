package com.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer>{
	

}
