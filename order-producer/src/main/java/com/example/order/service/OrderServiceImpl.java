package com.example.order.service;

import com.example.order.entities.Order;
import com.example.order.kafkaconfig.KafkaConfiguration;
import com.example.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, Order> kafkaTemplate;

    @Override
    public Order createOrder(Order order) {
        //set the order key
        order.setOrderKey(UUID.randomUUID().toString());

        Order savedOrder = orderRepository.save(order);
       try {
            kafkaTemplate.send(KafkaConfiguration.ORDERS_TOPIC, order.getOrderKey(), savedOrder);
        }catch(Exception ex){
            System.out.println("Error Sending Order To Kafka: " + ex.getMessage());
        }

        return savedOrder;
    }
}
