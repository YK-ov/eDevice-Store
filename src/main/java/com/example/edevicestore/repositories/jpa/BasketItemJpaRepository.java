package com.example.edevicestore.repositories.jpa;

import com.example.edevicestore.models.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BasketItemJpaRepository extends JpaRepository<BasketItem, String> {
    List<BasketItem> findByUserId(String userId);
    Optional<BasketItem> findByUserIdAndProductId(String userId, String productId);
    void deleteByUserId(String userId);
}
