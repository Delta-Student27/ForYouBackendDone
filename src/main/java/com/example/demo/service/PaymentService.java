package com.example.demo.service;

public interface PaymentService {

    String createPayment(Long orderId);

    String verifyPayment(String orderId,
                         String paymentId,
                         String signature);

    String processPayment(Long orderId, String email);
}