package com.example.order.consumer;


import com.example.order.entities.Order;
import com.example.order.entities.ProcessedOrder;
import com.example.order.repository.ProcessedOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
@RequiredArgsConstructor
public class OrderConsumer {

    private final ProcessedOrderRepository repository;

    @KafkaListener(topics = "orders", groupId = "orders-group")
    public void consume(Order order) {
        try {
            ProcessedOrder processed = new ProcessedOrder();
            processed.setOrderKey(order.getOrderKey());
            processed.setProduct(order.getProduct());
            processed.setQuantity(order.getQuantity());
            processed.setPrice(order.getPrice());
            processed.setProcessedAt(LocalDateTime.now());

            repository.save(processed);
            System.out.println("Processed order: " + order.getOrderKey());
        } catch (Exception e) {
            System.err.println("Error processing order: " + e.getMessage());

        }
    }
}
