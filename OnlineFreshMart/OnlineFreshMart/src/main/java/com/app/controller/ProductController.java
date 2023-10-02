package com.app.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.ProductAddRequest;
import com.app.entity.Category;
import com.app.entity.Product;
import com.app.repository.CategoryRepository;
import com.app.service.ProductService;

@RestController
@RequestMapping("api/product")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryDao;

    // Endpoint to add a new product
    @PostMapping("add")
    public ResponseEntity<?> addProduct(ProductAddRequest productDto) {
        System.out.println("received request for ADD PRODUCT");
        System.out.println(productDto);
        Product product = ProductAddRequest.toEntity(productDto);

        // Retrieve the category from the database using the provided categoryId
        Optional<Category> optional = categoryDao.findById(productDto.getCategoryId());
        Category category = null;
        if (optional.isPresent()) {
            category = optional.get();
        }

        product.setCategory(category);
        productService.addProduct(product, productDto.getImage());

        System.out.println("response sent!!!");
        return ResponseEntity.ok(product);
    }

    // Endpoint to get all products
    @GetMapping("all")
    public ResponseEntity<List<Product>> getAllProducts() {
        System.out.println("request came for getting all products");

        List<Product> products = productService.getAllProducts();

        System.out.println("response sent!!!");

        return ResponseEntity.ok(products);
    }

    // Endpoint to get a product by its productId
    @GetMapping("id")
    public ResponseEntity<Product> getProductById(@RequestParam("productId") int productId) {
        System.out.println("request came for getting Product by Product Id");

        Product product = productService.getProductById(productId);

        System.out.println("response sent!!!");

        return ResponseEntity.ok(product);
    }

    // Endpoint to get products by category
    @GetMapping("category")
    public ResponseEntity<List<Product>> getProductsByCategories(@RequestParam("categoryId") int categoryId) {
        System.out.println("request came for getting all products by category");

        List<Product> products = productService.getProductsByCategory(categoryId);

        System.out.println("response sent!!!");

        return ResponseEntity.ok(products);
    }

    // Endpoint to fetch product image by its name
    @GetMapping(value="/{productImageName}", produces = "image/*")
    public void fetchProductImage(@PathVariable("productImageName") String productImageName, HttpServletResponse resp) {
        System.out.println("request came for fetching product pic");
        System.out.println("Loading file: " + productImageName);

        productService.fetchProductImage(productImageName, resp);

        System.out.println("response sent!");
    }

    // Endpoint to get a product by productId and categoryId
    @GetMapping("/product/{productId}/category/{categoryId}")
    public ResponseEntity<Product> getProductByCategory(@PathVariable int productId, @PathVariable int categoryId) {
        System.out.println("request came for getting Product by Product Id and Category Id");

        Product product = productService.getProductByCategory(productId, categoryId);
        System.out.println(product);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        System.out.println("response sent!!!");
        return ResponseEntity.ok(product);
    }
}
