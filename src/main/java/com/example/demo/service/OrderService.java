package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Order;

public interface OrderService {

    Order placeOrder(String email);

    List<Order> getOrdersByUser(String email);
}
