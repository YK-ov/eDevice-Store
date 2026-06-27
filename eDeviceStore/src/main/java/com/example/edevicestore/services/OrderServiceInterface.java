package com.example.edevicestore.services;

import com.example.edevicestore.models.Order;
import com.example.edevicestore.models.OrderStatus;

import java.util.List;

public interface OrderServiceInterface {
    Order createOrderFromBasket(String userId);
    Order orderSingleProduct(String userId, String productId);
    List<Order> getOrderByUserId(String userId);
    Order updateOrderStatus(String orderId, OrderStatus status);
    List<Order> getAllOrders();
    String createPaymentSession(Order order);
}
