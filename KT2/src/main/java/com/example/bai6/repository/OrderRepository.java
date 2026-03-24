package com.example.bai6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.bai6.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}