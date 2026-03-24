package com.example.bai6.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.bai6.model.Product;
import com.example.bai6.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Lấy tất cả sản phẩm (không pagination)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Lấy tất cả sản phẩm có pagination
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    // Search theo tên + pagination
    public Page<Product> searchByName(String keyword, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCase(keyword, pageable);
    }

    // Lấy theo ID
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    // Lưu hoặc cập nhật
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    // Xóa
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Upload và cập nhật hình ảnh
    public void updateImage(Product product, MultipartFile imageFile) {

        if (imageFile == null || imageFile.isEmpty()) {
            return;
        }

        String contentType = imageFile.getContentType();

        if (contentType == null || !contentType.startsWith("image")) {
            throw new IllegalArgumentException("File tải lên không phải hình ảnh!");
        }

        try {

            Path uploadDir = Paths.get("src/main/resources/static/images/");

            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            String newFileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();

            Path filePath = uploadDir.resolve(newFileName);

            Files.copy(
                    imageFile.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            product.setImage(newFileName);

        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi lưu hình ảnh", e);
        }
    }
    // Lọc theo category
public Page<Product> getByCategory(Long categoryId, Pageable pageable) {
    return productRepository.findByCategoryId(categoryId, pageable);
}

// Search theo tên + category
public Page<Product> searchByNameAndCategory(String keyword, Long categoryId, Pageable pageable) {
    return productRepository.findByNameContainingIgnoreCaseAndCategoryId(keyword, categoryId, pageable);
}
    
}