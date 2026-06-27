package com.example.edevicestore.repositories.jpa;

import com.example.edevicestore.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderJpaRepository extends JpaRepository<Order, String> {
    List<Order> findByItemsProductId(String productId);
    List<Order> findByUserId(String orderId);
}
