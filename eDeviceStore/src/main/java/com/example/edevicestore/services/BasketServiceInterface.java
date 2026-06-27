package com.example.edevicestore.services;

import com.example.edevicestore.models.Basket;

public interface BasketServiceInterface {
    Basket getBasketByUserId(String userId);
    void addToBasket(String userId, String productId);
    void removeFromBasket(String userId, String productId);
    void clearBasket(String userId);
}
