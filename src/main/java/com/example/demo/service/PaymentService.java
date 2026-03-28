// package com.example.demo.service;
// import java.util.Map;
// public interface PaymentService {

//     //String createPayment(Long orderId);
//     Map<String, Object> createPayment(Long orderId);

//     String verifyPayment(String orderId,
//                          String paymentId,
//                          String signature);

//     String processPayment(Long orderId, String email);
// }
package com.example.demo.service;
import java.util.Map;
public interface PaymentService {

    //String createPayment(Long orderId);
    Map<String, Object> createPayment(Long orderId);

    String verifyPayment(String orderId,
                         String paymentId,
                         String signature);

    String processPayment(Long orderId, String email);
}