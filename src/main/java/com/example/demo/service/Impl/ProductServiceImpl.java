package com.example.demo.service.Impl;

import java.util.List;
import org.springframework.stereotype.Service;
<<<<<<< HEAD
import org.springframework.transaction.annotation.Transactional; // Import this
=======
import com.example.demo.model.Category;
>>>>>>> 741898d3e74e4280ae07b80cadfddd417e0b471b
import com.example.demo.model.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    
    private final ProductRepository productRepository;
    
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product addProduct(Product product) {
<<<<<<< HEAD
=======


        Long categoryId = product.getCategory().getId();

        
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setCategory(category);

       
>>>>>>> 741898d3e74e4280ae07b80cadfddd417e0b471b
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    
    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // 🔥 Added the Transactional Delete Logic
    @Override
    @Transactional
    public void deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }
}