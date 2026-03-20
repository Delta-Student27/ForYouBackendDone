package com.example.demo.service.Impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.EmailService;
import com.example.demo.service.PaymentService;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;
import com.razorpay.RazorpayException;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Value("${razorpay.key}")
    private String key;

    @Value("${razorpay.secret}")
    private String secret;

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final EmailService emailService;

    public PaymentServiceImpl(OrderRepository orderRepository,
                              UserRepository userRepository,
                              PaymentRepository paymentRepository,
                              EmailService emailService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
        this.emailService = emailService;
    }

    // 🔹 CREATE RAZORPAY ORDER
    // @Override
    // public String createPayment(Long orderId) {

    //     try {
    //         Order order = orderRepository.findById(orderId)
    //                 .orElseThrow(() -> new RuntimeException("Order not found"));

    //         RazorpayClient client = new RazorpayClient(key, secret);

    //         JSONObject options = new JSONObject();
    //         options.put("amount", (int) order.getTotalAmount() * 100);
    //         options.put("currency", "INR");
    //         options.put("receipt", "txn_" + System.currentTimeMillis());

    //         com.razorpay.Order razorpayOrder = client.orders.create(options);

    //         Payment payment = new Payment();
    //         payment.setAmount(order.getTotalAmount());
    //         payment.setPaymentStatus(PaymentStatus.PENDING); // ✅ ENUM
    //         payment.setRazorpayOrderId(razorpayOrder.get("id"));
    //         payment.setOrder(order);

    //         paymentRepository.save(payment);

    //         return razorpayOrder.toString();

    //     } catch (Exception e) {
    //         throw new RuntimeException("Payment creation failed: " + e.getMessage());
    //     }
    // }

    // // 🔹 VERIFY PAYMENT
    // @Override
    // public String verifyPayment(String orderId,
    //                             String paymentId,
    //                             String signature) {

    //     try {

    //         JSONObject options = new JSONObject();
    //         options.put("razorpay_order_id", orderId);
    //         options.put("razorpay_payment_id", paymentId);
    //         options.put("razorpay_signature", signature);

    //         boolean isValid = Utils.verifyPaymentSignature(options, secret);

    //         if (!isValid) {
    //             return "Invalid Signature";
    //         }

    //         Payment payment = paymentRepository
    //                 .findByRazorpayOrderId(orderId)
    //                 .orElseThrow(() -> new RuntimeException("Payment not found"));

    //         // ✅ Update Payment Status
    //         payment.setPaymentStatus(PaymentStatus.SUCCESS);
    //         payment.setRazorpayPaymentId(paymentId);
    //         paymentRepository.save(payment);

    //         // ✅ Update Order Status
    //         Order order = payment.getOrder();
    //         order.setStatus(OrderStatus.CONFIRMED); 
    //         orderRepository.save(order);

    //         return "Payment Verified Successfully";

    //     } catch (RazorpayException e) {
    //         throw new RuntimeException("Verification failed: " + e.getMessage());
    //     }
    // }
    @Override
public Map<String, Object> createPayment(Long orderId) {

    try {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        RazorpayClient client = new RazorpayClient(key, secret);

        JSONObject options = new JSONObject();
        int amount = (int) (order.getTotalAmount() * 100); // 🔥 correct

        options.put("amount", amount);
        options.put("currency", "INR");
        options.put("receipt", "txn_" + System.currentTimeMillis());

        com.razorpay.Order razorpayOrder = client.orders.create(options);

        Payment payment = new Payment();
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setRazorpayOrderId(razorpayOrder.get("id"));
        payment.setOrder(order);

        paymentRepository.save(payment);

        // 🔥 RETURN MAP INSTEAD OF STRING
        Map<String, Object> response = new HashMap<>();
        response.put("razorpayOrderId", razorpayOrder.get("id"));
        response.put("amount", amount);
        System.out.println("Order ID: " + orderId);
        System.out.println("Total Amount: " + order.getTotalAmount());
        return response;
        
    } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException("Payment creation failed: " + e.getMessage());
    }
}

    // 🔹 PROCESS PAYMENT (Manual Confirm + Email)
    @Override
    public String processPayment(Long orderId, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized payment attempt");
        }

        // ✅ Update Order Status
        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);

        try {
            emailService.sendEmail(
                    user.getEmail(),
                    "Payment Successful ✅",
                    "Hi " + user.getName() + ",\n\n"
                            + "Your payment was successful.\n\n"
                            + "Order ID: " + order.getId() + "\n"
                            + "Amount: ₹" + order.getTotalAmount() + "\n\n"
                            + "Thank you for shopping with us!"
            );
        } catch (Exception e) {
            System.out.println("Email failed but payment processed.");
        }

        return "Payment successful for Order #" + orderId;
    }
}