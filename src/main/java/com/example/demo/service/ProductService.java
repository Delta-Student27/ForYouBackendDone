package com.example.demo.service;

import java.util.List;
import com.example.demo.model.Product;

public interface ProductService {
    Product addProduct(Product product);
    List<Product> getAllProducts();
    Product getProductById(Long id);
    
    // Declaration for delete
    void deleteProduct(Long id);
}