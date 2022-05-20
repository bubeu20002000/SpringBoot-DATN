package com.example.login.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.login.model.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

}
