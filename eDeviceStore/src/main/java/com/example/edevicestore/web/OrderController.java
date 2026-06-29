package com.example.edevicestore.web;

import com.example.edevicestore.models.Order;
import com.example.edevicestore.models.OrderStatus;
import com.example.edevicestore.services.impl.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")

public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/basket/{userId}")
    public ResponseEntity<Order> createOrderFromBasket(@PathVariable String userId) {
        Order order = orderService.createOrderFromBasket(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @PostMapping("/single/{userId}/{productId}")
    public ResponseEntity<Order> orderSingleProduct(@PathVariable String userId, @PathVariable String productId) {
        Order order = orderService.orderSingleProduct(userId, productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable String userId) {
        return ResponseEntity.ok(orderService.getOrderByUserId(userId));
    }

    @PatchMapping("/{orderId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable String orderId, @RequestParam OrderStatus status) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

}
