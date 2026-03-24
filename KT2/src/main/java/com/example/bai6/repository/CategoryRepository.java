package com.example.bai6.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bai6.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}