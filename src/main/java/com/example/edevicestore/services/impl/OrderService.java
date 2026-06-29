package com.example.edevicestore.services.impl;

import com.example.edevicestore.models.*;
import com.example.edevicestore.repositories.OrderRepository;
import com.example.edevicestore.repositories.ProductRepository;
import com.example.edevicestore.repositories.UserRepository;
import com.example.edevicestore.services.OrderServiceInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional

public class OrderService implements OrderServiceInterface {
    private final OrderRepository orderRepository;
    private final BasketService basketService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, BasketService basketService, UserRepository userRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.basketService = basketService;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }


    @Override
    @Transactional
    public Order createOrderFromBasket(String userId) {
        Optional<User> foundUser = userRepository.findById(userId);

        if (foundUser.isEmpty()) {
            throw new IllegalArgumentException("Uzytkownika o id " + userId + " nie znaleziono w systemie");
        }

        Basket basket = basketService.getBasketByUserId(userId);

        List<BasketItem> usersBasketItems = basket.getAddedProducts();

        if (usersBasketItems.isEmpty()) {
            throw new IllegalStateException("Kosz jest pusty, nie mozna zlozyc zamowienia");
        }

        Order order = Order.builder().id(UUID.randomUUID().toString()).user(foundUser.get()).status(OrderStatus.pending).
                purchaseDate(LocalDateTime.now().toString()).items(new ArrayList<>()).build();

        BigDecimal totalPrice = BigDecimal.ZERO;

        for (int i = 0; i < usersBasketItems.size(); i++) {
            Product product = usersBasketItems.get(i).getProduct();

            OrderItem orderItem = OrderItem.builder().id(UUID.randomUUID().toString()).order(order).product(product)
                    .quantity(usersBasketItems.get(i).getQuantity()).purchasePrice(product.getPrice()).build();

            order.getItems().add(orderItem);

            product.setQuantity(product.getQuantity() - usersBasketItems.get(i).getQuantity());
            productRepository.save(product);

            BigDecimal currentPrice = usersBasketItems.get(i).getProduct().getPrice().multiply(BigDecimal.valueOf(usersBasketItems.get(i).getQuantity()));

            totalPrice = totalPrice.add(currentPrice);
        }

        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);

        basketService.clearBasket(userId);

        return savedOrder;
    }

    @Override
    @Transactional
    public Order orderSingleProduct(String userId, String productId) {
        Optional<User> foundUser = userRepository.findById(userId);
        Optional<Product> foundProduct = productRepository.findById(productId);

        if (foundUser.isEmpty()) {
            throw new IllegalArgumentException("Uzytkownika o id " + userId + " nie znaleziono w systemie");
        }

        if (foundProduct.isEmpty()) {
            throw new IllegalArgumentException("Produkt o id " + productId + " nie znaleziono w systemie");
        }

        Order order = Order.builder().id(UUID.randomUUID().toString()).user(foundUser.get()).status(OrderStatus.pending).
                purchaseDate(LocalDateTime.now().toString()).items(new ArrayList<>()).build();

        OrderItem orderItem = OrderItem.builder().id(UUID.randomUUID().toString()).order(order).product(foundProduct.get())
                .quantity(1).purchasePrice(foundProduct.get().getPrice()).build();

        order.getItems().add(orderItem);

        Product product = foundProduct.get();
        product.setQuantity(product.getQuantity() - 1);
        productRepository.save(product);

        order.setTotalPrice(foundProduct.get().getPrice());

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrderByUserId(String userId) {
        Optional<User> foundUser = userRepository.findById(userId);

        if (foundUser.isEmpty()) {
            throw new IllegalArgumentException("Uzytkownika o id " + userId + " nie znaleziono w systemie");
        }

        return orderRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public Order updateOrderStatus(String orderId, OrderStatus status) {
        Optional<Order> foundOrder = orderRepository.findById(orderId);

        if (foundOrder.isEmpty()) {
            throw new IllegalArgumentException("Zamowienia o id " + orderId + " nie znaleziono w systemie");
        }

        foundOrder.get().setStatus(status);
        orderRepository.save(foundOrder.get());

        return foundOrder.get();
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public String createPaymentSession(Order order) {
        return "";
    }
}
