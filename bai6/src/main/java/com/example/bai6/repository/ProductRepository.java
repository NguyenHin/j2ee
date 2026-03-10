package com.example.bai6.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bai6.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}