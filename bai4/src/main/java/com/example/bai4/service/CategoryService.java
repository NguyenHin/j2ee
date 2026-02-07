package com.example.bai4.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.bai4.model.Category;

@Service
public class CategoryService {

    private final List<Category> listCategory = new ArrayList<>();

    public CategoryService() {
        // Dữ liệu mẫu
        listCategory.add(new Category(1, "Điện thoại"));
        listCategory.add(new Category(2, "Laptop"));
        listCategory.add(new Category(3, "Phụ kiện"));
    }

    // Lấy tất cả danh mục
    public List<Category> getAll() {
        return listCategory;
    }

    // Lấy danh mục theo id
    public Category get(int id) {
        return listCategory.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }
}