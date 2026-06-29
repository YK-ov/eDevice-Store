package com.example.edevicestore.services.impl;

import com.example.edevicestore.models.Order;
import com.example.edevicestore.models.OrderStatus;
import com.example.edevicestore.models.Payment;
import com.example.edevicestore.models.PaymentStatus;
import com.example.edevicestore.repositories.OrderRepository;
import com.example.edevicestore.repositories.PaymentRepository;
import com.example.edevicestore.services.PaymentServiceInterface;
import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class PaymentService implements PaymentServiceInterface{
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    @Value("${stripe.api-key}")
    private String apiKey;

    @Value("${stripe.webhook-secret}")
    private String webhookSecret;

    @Value("${stripe.success-url}")
    private String successUrl;

    @Value("${stripe.cancel-url}")
    private String cancelUrl;

    public PaymentService(OrderRepository orderRepository, PaymentRepository paymentRepository) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public String createCheckOutSession(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Nie znaleziono zamowienia"));

        if (order.getStatus() != OrderStatus.pending){
            throw new IllegalStateException("Zamowienie nie oczekuje na platnosc lub zostalo juz oplacone");
        }

        Stripe.apiKey = apiKey;
        long amount = order.getTotalPrice().multiply(BigDecimal.valueOf(100)).longValue();

        SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName("Zamowienie numer: " + order.getId())
                .build();

        SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("pln")
                .setUnitAmount(amount)
                .setProductData(productData)
                .build();

        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setQuantity(Long.valueOf(1))
                .setPriceData(priceData)
                .build();

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(successUrl)
                .setCancelUrl(cancelUrl)
                .addLineItem(lineItem)
                .putMetadata("orderId", order.getId())
                .build();

        try {
            Session session = Session.create(params);

            Payment payment = paymentRepository.findByOrderId(order.getId()).orElseGet(() -> Payment.builder().
                    id(UUID.randomUUID().toString())
                    .order(order)
                    .createdAt(LocalDateTime.now().toString())
                    .build());

            payment.setAmount(amount);
            payment.setCurrency("pln");
            payment.setStripeSessionId(session.getId());
            payment.setStatus(PaymentStatus.PENDING);

            paymentRepository.save(payment);


            return session.getUrl();
        }
        catch (StripeException e) {
            throw new RuntimeException("Blad podczas tworzenia sesji platnosci " + e.getMessage());
        }
    }

    @Override
    public void handleWebhook(String payload, String signature) {
        Stripe.apiKey = apiKey;

        Event event;

        try {
            event = Webhook.constructEvent(payload, signature, webhookSecret);
        } catch (SignatureVerificationException e){
            throw new IllegalArgumentException("Nieprawidlowy podpis Stripe webhook");
        }

        if (!"checkout.session.completed".equals(event.getType())){
            return;
        }

        String rawJson = event.getDataObjectDeserializer().getRawJson();
        Session session = com.stripe.net.ApiResource.GSON.fromJson(rawJson, Session.class);

        if (session == null || session.getId() == null) {
            throw new IllegalStateException("Nie udalo sie odczytac sesji Stripe");
        }

        String stripeSessionId = session.getId();

        Payment payment = paymentRepository.findByStripeSessionId(stripeSessionId).orElseThrow(() -> new IllegalStateException("Nie znaleziono platnosci dla sesji Stripe"));

        if (payment.getStatus() == PaymentStatus.PAID){
            return;
        }

        payment.setStatus(PaymentStatus.PAID);
        payment.setPaidAt(LocalDateTime.now().toString());
        paymentRepository.save(payment);

        Order order = payment.getOrder();

        if (order.getStatus() == OrderStatus.pending){
            order.setStatus(OrderStatus.processing);
            orderRepository.save(order);
        }
    }
}