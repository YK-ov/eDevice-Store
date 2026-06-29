package com.example.edevicestore.repositories;

import com.example.edevicestore.models.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    List<Order> findAll();
    Optional<Order> findById(String orderId);
    Order save(Order order);
    void deleteById(String orderId);
    List<Order> findByProductId(String productId);
    List<Order> findByUserId(String userId);
    void deleteAll(List<Order> orders);
}
