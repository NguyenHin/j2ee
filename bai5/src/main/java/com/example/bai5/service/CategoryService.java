package com.example.bai5.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bai5.model.Category;
import com.example.bai5.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Lấy tất cả danh mục
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Lưu hoặc cập nhật danh mục
    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    // Lấy theo ID
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    // Xóa theo ID
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}