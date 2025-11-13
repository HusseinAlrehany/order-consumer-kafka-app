package com.example.order.entities;

import lombok.Data;

@Data
public class Order {

    private Long id;

    private String product;
    private int quantity;
    private double price;
    private String orderKey;
}
