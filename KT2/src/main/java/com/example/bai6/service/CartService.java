package com.example.bai6.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.example.bai6.model.CartItem;
import com.example.bai6.model.Product;
import com.example.bai6.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
@SessionScope
public class CartService {

    private List<CartItem> items = new ArrayList<>();

    @Autowired
    private ProductRepository productRepository;


    public List<CartItem> getItems() {
        return items;
    }

    // 🔥 Đổi int → Long
    public void addToCart(Long productId) {

        Product findProduct = productRepository.findById(productId).orElse(null);

        if (findProduct == null) {
            return;
        }

        items.stream()
                .filter(item -> item.getId().equals(productId)) // dùng equals
                .findFirst()
                .ifPresentOrElse(
                        item -> item.setQuantity(item.getQuantity() + 1),
                        () -> {
                            CartItem newItem = new CartItem();
                            newItem.setId(productId); // Long
                            newItem.setName(findProduct.getName());
                            newItem.setImage(findProduct.getImage());
                            newItem.setPrice(findProduct.getPrice());
                            newItem.setQuantity(1);
                            items.add(newItem);
                        }
                );
    }

    public void updateQuantity(Long productId, int quantity) {
        items.stream()
                .filter(item -> item.getId().equals(productId))
                .findFirst()
                .ifPresent(item -> item.setQuantity(quantity));
    }

    public void removeFromCart(Long productId) {
        items.removeIf(item -> item.getId().equals(productId));
    }

    public void clear() {
        items.clear();
    }

    public double getTotal() {
        return items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
    
}