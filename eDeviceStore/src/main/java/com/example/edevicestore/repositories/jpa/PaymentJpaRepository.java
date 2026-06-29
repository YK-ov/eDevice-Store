package com.example.edevicestore.repositories.jpa;

import com.example.edevicestore.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.file.PathMatcher;
import java.util.Optional;

public interface PaymentJpaRepository extends JpaRepository<Payment, String> {
    Optional<Payment> findByStripeSessionId(String stripeSessionId);
    Optional<Payment> findByOrder_Id(String orderId);
}
