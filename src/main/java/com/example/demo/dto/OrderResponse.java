package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {

    public Long orderId;
    public String orderStatus;
    public Double totalAmount;
    public LocalDateTime orderDate;
    public Long userId;
    //public List<OrderItemResponse> items;
}
