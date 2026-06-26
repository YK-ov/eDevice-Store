package models.repositories.jpa;

import models.Order;
import models.repositories.OrderRepository;
import org.aspectj.weaver.ast.Or;
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
    public Optional<Order> findByProductId(String productId) {
        return delegate.findByProductId(productId);
    }

    @Override
    public Optional<Order> findByUserId(String userId) {
        return delegate.findByUserId(userId);
    }

}
