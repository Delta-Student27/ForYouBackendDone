package com.example.demo.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.Order;
import com.example.demo.service.PaymentService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @PostMapping("/create")
    public String createPayment(@RequestParam Long orderId) {
        return paymentService.createPayment(orderId);
    }
    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(
            @RequestParam String razorpay_order_id,
            @RequestParam String razorpay_payment_id,
            @RequestParam String razorpay_signature) {

        return ResponseEntity.ok(
                paymentService.verifyPayment(
                        razorpay_order_id,
                        razorpay_payment_id,
                        razorpay_signature));
                }
    @PostMapping("/pay")
    public String makePayment(@RequestParam Long orderId) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return paymentService.processPayment(orderId, email);

    }

}