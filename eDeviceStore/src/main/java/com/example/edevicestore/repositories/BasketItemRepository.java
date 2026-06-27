package com.example.edevicestore.repositories;

import com.example.edevicestore.models.BasketItem;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface BasketItemRepository {
    List<BasketItem> findByUserId(String userId);
    Optional<BasketItem> findByUserIdAndProductId(String userId, String productId);
    void deleteAllByUserId(String userId);
    BasketItem save(BasketItem basketItem);
    void deleteById(String basketItemId);
}
