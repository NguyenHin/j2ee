package com.example.bai6.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bai6.model.*;
import com.example.bai6.repository.*;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public void checkout(List<CartItem> cartItems) {

        Order order = new Order();

        order.setOrderDate(new Date());

        double total = 0;
        order.setPaid(false);

        List<OrderDetail> details = new ArrayList<>();

        for (CartItem item : cartItems) {

            OrderDetail detail = new OrderDetail();

            Product product = productRepository
                    .findById(item.getId())
                    .orElse(null);

            detail.setProduct(product);

            detail.setQuantity(item.getQuantity());

            detail.setPrice(item.getPrice());

            detail.setOrder(order);

            total += item.getPrice() * item.getQuantity();

            details.add(detail);
        }

        order.setTotal(total);

        order.setOrderDetails(details);

        orderRepository.save(order);
    }
}
