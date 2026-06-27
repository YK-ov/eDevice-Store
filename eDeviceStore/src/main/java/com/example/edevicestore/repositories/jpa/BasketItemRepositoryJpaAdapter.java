package com.example.edevicestore.repositories.jpa;

import com.example.edevicestore.models.BasketItem;
import com.example.edevicestore.repositories.BasketItemRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("jpa")

public class BasketItemRepositoryJpaAdapter implements BasketItemRepository {
    private final BasketItemJpaRepository delegate;

    public BasketItemRepositoryJpaAdapter(BasketItemJpaRepository delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<BasketItem> findByUserId(String userId) {
        return delegate.findByUserId(userId);
    }

    @Override
    public Optional<BasketItem> findByUserIdAndProductId(String userId, String productId) {
        return delegate.findByUserIdAndProductId(userId, productId);
    }

    @Override
    public void deleteAllByUserId(String userId) {
        delegate.deleteByUserId(userId);
    }

    @Override
    public BasketItem save(BasketItem basketItem) {
        return delegate.save(basketItem);
    }

    @Override
    public void deleteById(String basketItemId) {
        delegate.deleteById(basketItemId);
    }
}
