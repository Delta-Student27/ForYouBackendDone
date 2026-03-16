package com.example.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Cart;
import com.example.demo.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // ✅ Helper method to get email from Security Context
    private String getAuthenticatedUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    // ✅ Add product to cart
    @PostMapping("/add")
    public Cart addToCart(@RequestParam Long productId,
                          @RequestParam int quantity) {
        return cartService.addToCart(getAuthenticatedUserEmail(), productId, quantity);
    }

    // ✅ Get logged-in user's cart
    @GetMapping
    public Cart getMyCart() {
        return cartService.getCartByUserEmail(getAuthenticatedUserEmail());
    }

    // ✅ NEW: Remove specific product from cart
    @PostMapping("/remove")
    public void removeFromCart(@RequestParam Long productId) {
        cartService.removeFromCart(getAuthenticatedUserEmail(), productId);
    }

    // ✅ NEW: Clear entire cart
    @PostMapping("/clear")
    public void clearCart() {
        cartService.clearCart(getAuthenticatedUserEmail());
    }
}