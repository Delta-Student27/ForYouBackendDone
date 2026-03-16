package com.example.demo.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Order;
import com.example.demo.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place")
    public Order placeOrder() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return orderService.placeOrder(email);
    }

    @GetMapping
    public List<Order> getOrders() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return orderService.getOrdersByUser(email);
    }
}
