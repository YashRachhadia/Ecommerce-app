package com.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.model.Authorities;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities, Integer> {

}
