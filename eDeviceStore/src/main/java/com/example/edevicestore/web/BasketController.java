package com.example.edevicestore.web;

import com.example.edevicestore.models.Basket;
import com.example.edevicestore.services.impl.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/basket")

public class BasketController {
    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Basket> getBasket(@PathVariable String userId) {
        return ResponseEntity.ok(basketService.getBasketByUserId(userId));
    }

    @PostMapping("/{userId}/add/{productId}")
    public ResponseEntity<Void> addToBasket(@PathVariable String userId, @PathVariable String productId) {
        basketService.addToBasket(userId, productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/remove/{productId}")
    public ResponseEntity<Void> removeFromBasket(@PathVariable String userId, @PathVariable String productId) {
        basketService.removeFromBasket(userId, productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<Void> clearBasket(@PathVariable String userId) {
        basketService.clearBasket(userId);
        return ResponseEntity.ok().build();
    }

}
