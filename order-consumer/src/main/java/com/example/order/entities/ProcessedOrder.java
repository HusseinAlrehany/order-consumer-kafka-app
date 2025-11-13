package com.example.order.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Entity
@Table
@Data
public class ProcessedOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderKey;
    private String product;
    private int quantity;
    private double price;
    private LocalDateTime processedAt;
}
