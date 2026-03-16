package com.example.demo.model;

public enum OrderStatus {

    CREATED,
    PROCESSING,
    PLACED,
    CONFIRMED,
    PAID,          // ✅ ADD THIS
    SHIPPED,
    DELIVERED,
    CANCELLED
}
