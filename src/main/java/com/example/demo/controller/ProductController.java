// package com.example.demo.controller;
// import java.util.List;

// import java.util.Map;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.web.multipart.MultipartFile;

// import com.example.demo.service.ProductService;
// import com.example.demo.service.FileService;
// import com.example.demo.service.CategoryService;
// import com.example.demo.model.Product;
// import com.example.demo.model.Category;

// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
// import com.example.demo.service.ProductService;
// import com.example.demo.service.Impl.ProductServiceImpl;
// import com.example.demo.model.Product;
// import org.springframework.web.bind.annotation.PathVariable;


// @RestController
// @RequestMapping("/api/products")
// public class ProductController {

//     private final ProductService productService;
//     private final FileService fileService;
//     private final CategoryService categoryService;


//     public ProductController(ProductService productService, 
//                              FileService fileService, 
//                              CategoryService categoryService) {

//     //private final ProductServiceImpl productServiceImpl;

//     public ProductController(ProductService productService) {

//         this.productService = productService;
//         this.fileService = fileService;
//         this.categoryService = categoryService;
//     }


//     /**
//      * POST: Add a new product with an image upload
//      */
//     @PostMapping("/add")
//     public ResponseEntity<?> addProduct(
//             @RequestParam("name") String name,
//             @RequestParam("description") String description,
//             @RequestParam("price") double price,
//             @RequestParam("stock") int stock,
//             @RequestParam("categoryId") Long categoryId,
//             @RequestParam("image") MultipartFile file) {

//         try {
//             if (file.isEmpty()) {
//                 return ResponseEntity.badRequest().body("Please upload an image file.");
//             }

//             // 1. Upload to Cloudinary via FileService
//             String imageUrl = fileService.uploadFile(file);

//             // 2. Fetch Category from DB
//             Category category = categoryService.getCategoryById(categoryId);
//             if (category == null) {
//                 return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                                      .body("Error: Category with ID " + categoryId + " not found.");
//             }

//             // 3. Map to Product Entity
//             Product product = new Product();
//             product.setName(name);
//             product.setDescription(description);
//             product.setPrice(price);
//             product.setStock(stock);
//             product.setImageUrl(imageUrl);
//             product.setCategory(category);

//             // 4. Save to Database
//             Product savedProduct = productService.addProduct(product);

//             return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);

//         } catch (Exception e) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                  .body("Backend Error: " + e.getMessage());
//         }

//     // public ProductController(ProductServiceImpl productServiceImpl) {
//     //     this.productServiceImpl = productServiceImpl;
//     // }

//     @PostMapping
//     public Product addProduct(@RequestBody Product product) {
//         return productService.addProduct(product);

//     }
//     // @PostMapping("/products")
//     // public ResponseEntity<Product> addProduct(@RequestBody Product product) {
//     //     Product saved = productService.addProduct(product);
//     //     return ResponseEntity.ok(saved);
//     // }

//     // @PostMapping
//     // public Product addProduct(@RequestBody Product product) {
//     //     return productServiceImpl.addProduct(product);
//     // }

//     /**
//      * GET: Fetch all products
//      */
//     @GetMapping
//     public List<Product> getAll() {
//         return productService.getAllProducts();
//     }


//     /**
//      * DELETE: Remove a product by ID
//      */
//     @DeleteMapping("/{id}")
//     public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
//         try {
//             productService.deleteProduct(id);
//             // Returning a JSON Map ensures the frontend Axios call sees a clean success message
//             return ResponseEntity.ok().body(Map.of("message", "Deleted successfully"));
//         } catch (Exception e) {
//             // If there's a foreign key constraint (product in cart), this sends the 500 error
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                  .body(Map.of("error", "Error: " + e.getMessage()));
//         }
//     }
// }

//     @GetMapping("/{id}")
//     public Product getProductById(@PathVariable Long id) {
//         return productService.getProductById(id);
//     }

//     // @GetMapping
//     // public List<Product> getAll() {
//     //     return productServiceImpl.getAllProducts();
//     // }
// }


package com.example.demo.controller;

import java.util.List;
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

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final FileService fileService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService,
                             FileService fileService,
                             CategoryService categoryService) {
        this.productService = productService;
        this.fileService = fileService;
        this.categoryService = categoryService;
    }

    /**
     * POST: Add product with image upload
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

            // upload image
            String imageUrl = fileService.uploadFile(file);

            // get category
            Category category = categoryService.getCategoryById(categoryId);
            if (category == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Category not found");
            }

            // create product
            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setStock(stock);
            product.setImageUrl(imageUrl);
            product.setCategory(category);

            Product savedProduct = productService.addProduct(product);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Backend Error: " + e.getMessage());
        }
    }

    /**
     * POST: Add product without image
     */
    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    /**
     * GET: All products
     */
    @GetMapping
    public List<Product> getAll() {
        return productService.getAllProducts();
    }

    /**
     * GET: Product by ID
     */
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    /**
     * DELETE: Delete product
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(Map.of("message", "Deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}