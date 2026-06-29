package com.example.edevicestore.repositories.jpa;

import com.example.edevicestore.models.Order;
import com.example.edevicestore.repositories.OrderRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("jpa")

public class OrderRepositoryJpaAdapter implements OrderRepository {
    private final OrderJpaRepository delegate;

    public OrderRepositoryJpaAdapter(OrderJpaRepository delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<Order> findAll() {
        return delegate.findAll();
    }

    @Override
    public Optional<Order> findById(String orderId) {
        return delegate.findById(orderId);
    }

    @Override
    public Order save(Order order) {
        if (order.getId() == null || order.getId().isBlank()){
            order.setId(UUID.randomUUID().toString());
        }

        return delegate.save(order);
    }

    @Override
    public void deleteById(String orderId) {
        delegate.deleteById(orderId);
    }

    @Override
    public List<Order> findByProductId(String productId) {
        return delegate.findByItemsProductId(productId);
    }

    @Override
    public List<Order> findByUserId(String userId) {
        return delegate.findByUserId(userId);
    }

    @Override
    public void deleteAll(List<Order> orders) {
        delegate.deleteAll(orders);
    }

}
