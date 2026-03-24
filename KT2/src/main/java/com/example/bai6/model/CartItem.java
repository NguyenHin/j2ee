package com.example.bai6.model;

import lombok.Data;

@Data
public class CartItem {
    private Long id;
    private String name; 
    private String image; 
    private double price;
//Quantity
    private int quantity;
}
