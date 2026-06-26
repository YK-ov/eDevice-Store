package models.repositories;

import models.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    List<Order> findAll();
    Optional<Order> findById(String orderId);
    Order save(Order order);
    void deleteById(String orderId);
    Optional<Order> findByProductId(String productId);
    Optional<Order> findByUserId(String userId);
}
