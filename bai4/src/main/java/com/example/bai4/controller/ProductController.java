package com.example.bai4.controller;

import com.example.bai4.model.Category;
import com.example.bai4.model.Product;
import com.example.bai4.service.CategoryService;
import com.example.bai4.service.ProductService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    @GetMapping()
    public String Index(Model model) {
        model.addAttribute("listproduct", productService.getAll());
        return "product/products";
    }

    // Hiển thị form create
    @GetMapping("/create")
    public String Create(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAll());
        return "product/create";
    }

    // Xử lý create
    @PostMapping("/create")
    public String Create(@Valid @ModelAttribute("product") Product newProduct,
                         BindingResult result,
                         @RequestParam("category.id") int categoryId,
                         @RequestParam("imageProduct") MultipartFile imageProduct,
                         Model model) {

        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "product/create";
        }

        // xử lý upload ảnh
        productService.updateImage(newProduct, imageProduct);

        // set category
        Category selectedCategory = categoryService.get(categoryId);
        newProduct.setCategory(selectedCategory);

        productService.add(newProduct);

        return "redirect:/products";
    }

    // Hiển thị form edit
    @GetMapping("/edit/{id}")
    public String Edit(@PathVariable int id, Model model) {

        Product find = productService.get(id);

        if (find == null) {
            return "error/404";
        }

        model.addAttribute("product", find);
        model.addAttribute("categories", categoryService.getAll());

        return "product/edit";
    }

    // Xử lý edit
    @PostMapping("/edit")
    public String Edit(@Valid @ModelAttribute("product") Product editProduct,
                       BindingResult result,
                       @RequestParam("category.id") int categoryId,
                       @RequestParam("imageProduct") MultipartFile imageProduct,
                       Model model) {

        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "product/edit";
        }
        // nếu có upload ảnh mới thì update
        if (imageProduct != null && !imageProduct.isEmpty()) {
            productService.updateImage(editProduct, imageProduct);
        }

        // set category
        Category selectedCategory = categoryService.get(categoryId);
        editProduct.setCategory(selectedCategory);

        productService.update(editProduct);

        return "redirect:/products";
    }

    // Xóa sản phẩm
    @GetMapping("/delete/{id}")
    public String Delete(@PathVariable int id) {
        productService.delete(id);
        return "redirect:/products";
    }
}