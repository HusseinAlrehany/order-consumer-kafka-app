package com.example.order.entities;

import jakarta.persistence.*;
import lombok.Data;


//avoid using reserved word in mysql
//the table name "order" causes exception, since order is RESERVE WORD IN SQL
//so it changed to "orders"
@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String product;
    private int quantity;
    private double price;
    private String orderKey;


}
