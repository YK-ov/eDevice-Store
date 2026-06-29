package com.example.edevicestore.repositories;

import com.example.edevicestore.models.Order;
import com.example.edevicestore.models.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository {
    List<Payment> findAll();
    Optional<Payment> findById(String id);
    Optional<Payment> findByStripeSessionId(String stripeSessionId);
    Optional<Payment> findByOrderId(String orderId);
    Payment save(Payment payment);
}
