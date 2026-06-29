package com.example.edevicestore.services.impl;

import com.example.edevicestore.models.Basket;
import com.example.edevicestore.models.BasketItem;
import com.example.edevicestore.models.Product;
import com.example.edevicestore.models.User;
import com.example.edevicestore.repositories.BasketItemRepository;
import com.example.edevicestore.repositories.ProductRepository;
import com.example.edevicestore.repositories.UserRepository;
import com.example.edevicestore.services.BasketServiceInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional

public class BasketService implements BasketServiceInterface {
    private final UserRepository userRepository;
    private final BasketItemRepository basketItemRepository;
    private final ProductRepository productRepository;

    public BasketService(UserRepository userRepository, BasketItemRepository basketItemRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.basketItemRepository = basketItemRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Basket getBasketByUserId(String userId) {
        Optional<User> foundUser = userRepository.findById(userId);

        if (foundUser.isEmpty()) {
            throw new IllegalArgumentException("Uzytkownika o id " + userId + " nie znaleziono w systemie");
        }

        List<BasketItem> itemsInBasket = basketItemRepository.findByUserId(userId);

        return Basket.builder().user(foundUser.get()).addedProducts(itemsInBasket).build();
    }

    @Override
    @Transactional
    public void addToBasket(String userId, String productId) {
        Optional<User> foundUser = userRepository.findById(userId);
        Optional<Product> foundProduct = productRepository.findById(productId);

        if (foundUser.isEmpty()) {
            throw new IllegalArgumentException("Uzytkownika o id " + userId + " nie znaleziono w systemie");
        }

        if (foundProduct.isEmpty()) {
            throw new IllegalArgumentException("Produkt o id " + productId + " nie znaleziono w systemie");
        }

        Optional<BasketItem> basketItem = basketItemRepository.findByUserIdAndProductId(userId, productId);

        if (basketItem.isEmpty()) {
            BasketItem newBasketItem = BasketItem.builder()
                    .id(UUID.randomUUID().toString())
                    .user(foundUser.get())
                    .product(foundProduct.get())
                    .quantity(1)
                    .build();
            basketItemRepository.save(newBasketItem);
        }
        else {
            BasketItem existingItem = basketItem.get();
            existingItem.setQuantity(existingItem.getQuantity() + 1);
            basketItemRepository.save(existingItem);
        }
    }

    @Override
    @Transactional
    public void removeFromBasket(String userId, String productId) {
        Optional<User> foundUser = userRepository.findById(userId);
        Optional<Product> foundProduct = productRepository.findById(productId);

        if (foundUser.isEmpty()) {
            throw new IllegalArgumentException("Uzytkownika o id " + userId + " nie znaleziono w systemie");
        }

        if (foundProduct.isEmpty()) {
            throw new IllegalArgumentException("Produkt o id " + productId + " nie znaleziono w systemie");
        }

        Optional<BasketItem> basketItem = basketItemRepository.findByUserIdAndProductId(userId, productId);

        if (basketItem.isEmpty()) {
            throw new IllegalStateException("Produkt " + productId + " nie znaleziono w koszu");
        }

        if (basketItem.get().getQuantity() > 1){
            basketItem.get().setQuantity(basketItem.get().getQuantity() - 1);
            basketItemRepository.save(basketItem.get());
        }
        else {
            basketItemRepository.deleteById(basketItem.get().getId());
        }

    }

    @Override
    @Transactional
    public void clearBasket(String userId) {
        Optional<User> foundUser = userRepository.findById(userId);

        if (foundUser.isEmpty()) {
            throw new IllegalArgumentException("Uzytkownika o id " + userId + " nie znaleziono w systemie");
        }

        basketItemRepository.deleteAllByUserId(userId);
    }
}
