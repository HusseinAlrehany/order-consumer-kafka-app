package com.example.order.kafkaconsumer;

import com.example.order.entities.Order;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;
/*
* When using JsonSerializer/JsonDeserializer across apps,
* the consumer must have the same class with the same fully qualified package name
* as the producer. if not it throws exception ClassNotFound, and infinitely retries
* OR we can Share the Order class via common library, like create new maven module,
* or jar called order-model, move the order class there
* and then added as dependency for both consumer and producer, especially if the packages will be different
* NOTE: the kafka broker not needed on both apps, existence in producer is enough.
* */


@Configuration
public class KafkaConsumerConfig {


    @Bean
    public ConsumerFactory<String, Order> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "orders-group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        // Map the fully qualified class sent by producer to local class
        /*JsonDeserializer<Order> deserializer = new JsonDeserializer<>(Order.class);
        deserializer.addTrustedPackages("*"); // allow any package
        deserializer.setUseTypeMapperForKey(true);*/


        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                   new JsonDeserializer<>(Order.class));
    }



    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Order> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Order> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }


}
