// package com.example.demo.repository;

// import org.springframework.data.jpa.repository.JpaRepository;

// import com.example.demo.model.Payment;

// public interface PaymentRepository extends JpaRepository<Payment, Long> {
// }
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.example.demo.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByRazorpayOrderId(String razorpayOrderId);

}