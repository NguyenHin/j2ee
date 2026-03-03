package com.example.bai5.service;

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

import com.example.bai5.model.Product;
import com.example.bai5.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Lấy tất cả sản phẩm
    public List<Product> getAllProducts() {
        return productRepository.findAll();
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

        // Kiểm tra có phải file ảnh không
        String contentType = imageFile.getContentType();
        if (contentType == null || !contentType.startsWith("image")) {
            throw new IllegalArgumentException("File tải lên không phải hình ảnh!");
        }

        try {
            // Tạo thư mục nếu chưa có
            Path uploadDir = Paths.get("src/main/resources/static/images/");
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            // Tạo tên file mới tránh trùng
            String newFileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();

            Path filePath = uploadDir.resolve(newFileName);

            Files.copy(
                    imageFile.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            // Lưu tên file vào database
            product.setImage(newFileName);

        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi lưu hình ảnh", e);
        }
    }
}