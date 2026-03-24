package com.example.bai6.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="order_date")
    private Date orderDate;

    @Column(name = "total_amount")   // ⭐ map đúng cột DB
    private double total;

    @Column(name = "is_paid")
    private boolean isPaid;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;
}