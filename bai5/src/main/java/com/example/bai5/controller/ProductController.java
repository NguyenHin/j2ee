package com.example.bai5.controller;

import com.example.bai5.model.Product;
import com.example.bai5.service.CategoryService;
import com.example.bai5.service.ProductService;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    // Hiển thị danh sách sản phẩm
    @GetMapping
    public String listProducts(Model model) {
        List<Product> productList = productService.getAllProducts();
        model.addAttribute("products", productList);
        return "product/list"; 
    }

    // Hiển thị form thêm
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product/add";
    }

    // Lưu sản phẩm
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product,
                            @RequestParam("imageProduct") MultipartFile file) {

        // Nếu là EDIT (có id)
        if (product.getId() != null) {

            Product existingProduct = productService.getProductById(product.getId());

            if (existingProduct != null) {

                // Nếu không chọn ảnh mới → giữ ảnh cũ
                if (file.isEmpty()) {
                    product.setImage(existingProduct.getImage());
                } else {
                    productService.updateImage(product, file);
                }
            }

        } else {
            // Nếu là ADD mới
            if (!file.isEmpty()) {
                productService.updateImage(product, file);
            }
        }

        productService.saveProduct(product);

        return "redirect:/products";
    }
    // Hiển thị form sửa
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product/edit";
    }

    // Xóa sản phẩm
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}