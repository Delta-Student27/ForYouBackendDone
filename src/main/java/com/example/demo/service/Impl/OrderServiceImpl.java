package com.example.demo.service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.OrderStatus;
import com.example.demo.model.User;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.EmailService;
import com.example.demo.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final EmailService emailService;

    public OrderServiceImpl(UserRepository userRepository,
                            CartRepository cartRepository,
                            CartItemRepository cartItemRepository,
                            OrderRepository orderRepository,
                            OrderItemRepository orderItemRepository,
                            EmailService emailService) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.emailService = emailService;
    }

    @Override
    public Order placeOrder(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart is empty"));

        List<CartItem> cartItems = cartItemRepository.findByCart(cart);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);

        // ✅ FIXED: Use ENUM instead of String
        order.setStatus(OrderStatus.CREATED);

        Order savedOrder = orderRepository.save(order);

        double totalAmount = 0.0;

        for (CartItem cartItem : cartItems) {

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());

            totalAmount += cartItem.getQuantity() * cartItem.getProduct().getPrice();

            orderItemRepository.save(orderItem);
        }

        savedOrder.setTotalAmount(totalAmount);
        orderRepository.save(savedOrder);

        // ✅ Clear Cart After Order
        cartItemRepository.deleteAll(cartItems);

        // ✅ Send Order Confirmation Email
        try {
            emailService.sendEmail(
                    user.getEmail(),
                    "Order Confirmation 📦",
                    "Hi " + user.getName() + ",\n\n"
                            + "Your order #" + savedOrder.getId()
                            + " has been placed successfully.\n\n"
                            + "Total Amount: ₹" + totalAmount + "\n\n"
                            + "Thank you for shopping with us!"
            );
        } catch (Exception e) {
            System.out.println("Email failed but order placed.");
        }

        return savedOrder;
    }

    @Override
    public List<Order> getOrdersByUser(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return orderRepository.findByUser(user);
    }
}
