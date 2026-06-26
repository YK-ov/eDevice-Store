package models.repositories.jpa;

import models.Order;
import models.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderJpaRepository extends JpaRepository<Order, String> {

    Optional<Order> findByUserId(String orderId);
    Optional<Order> findByProductId(String productId);
}
