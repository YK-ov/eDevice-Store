package com.example.edevicestore.services;

import com.example.edevicestore.models.Order;

public interface PaymentServiceInterface {
    String createCheckOutSession(String orderId);
    void handleWebhook(String payload, String signature);
}
