package com.example.demo.controller;
import java.util.List;
<<<<<<< HEAD
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.ProductService;
import com.example.demo.service.FileService;
import com.example.demo.service.CategoryService;
import com.example.demo.model.Product;
import com.example.demo.model.Category;
=======
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.service.ProductService;
import com.example.demo.service.Impl.ProductServiceImpl;
import com.example.demo.model.Product;
import org.springframework.web.bind.annotation.PathVariable;
>>>>>>> 741898d3e74e4280ae07b80cadfddd417e0b471b

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final FileService fileService;
    private final CategoryService categoryService;

<<<<<<< HEAD
    public ProductController(ProductService productService, 
                             FileService fileService, 
                             CategoryService categoryService) {
=======
    //private final ProductServiceImpl productServiceImpl;

    public ProductController(ProductService productService) {
>>>>>>> 741898d3e74e4280ae07b80cadfddd417e0b471b
        this.productService = productService;
        this.fileService = fileService;
        this.categoryService = categoryService;
    }

<<<<<<< HEAD
    /**
     * POST: Add a new product with an image upload
     */
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("stock") int stock,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("image") MultipartFile file) {

        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Please upload an image file.");
            }

            // 1. Upload to Cloudinary via FileService
            String imageUrl = fileService.uploadFile(file);

            // 2. Fetch Category from DB
            Category category = categoryService.getCategoryById(categoryId);
            if (category == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("Error: Category with ID " + categoryId + " not found.");
            }

            // 3. Map to Product Entity
            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setStock(stock);
            product.setImageUrl(imageUrl);
            product.setCategory(category);

            // 4. Save to Database
            Product savedProduct = productService.addProduct(product);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Backend Error: " + e.getMessage());
        }
=======
    // public ProductController(ProductServiceImpl productServiceImpl) {
    //     this.productServiceImpl = productServiceImpl;
    // }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
>>>>>>> 741898d3e74e4280ae07b80cadfddd417e0b471b
    }
    // @PostMapping("/products")
    // public ResponseEntity<Product> addProduct(@RequestBody Product product) {
    //     Product saved = productService.addProduct(product);
    //     return ResponseEntity.ok(saved);
    // }

    // @PostMapping
    // public Product addProduct(@RequestBody Product product) {
    //     return productServiceImpl.addProduct(product);
    // }

    /**
     * GET: Fetch all products
     */
    @GetMapping
    public List<Product> getAll() {
        return productService.getAllProducts();
    }
<<<<<<< HEAD

    /**
     * DELETE: Remove a product by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            // Returning a JSON Map ensures the frontend Axios call sees a clean success message
            return ResponseEntity.ok().body(Map.of("message", "Deleted successfully"));
        } catch (Exception e) {
            // If there's a foreign key constraint (product in cart), this sends the 500 error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "Error: " + e.getMessage()));
        }
    }
}
=======
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    // @GetMapping
    // public List<Product> getAll() {
    //     return productServiceImpl.getAllProducts();
    // }
}
>>>>>>> 741898d3e74e4280ae07b80cadfddd417e0b471b
