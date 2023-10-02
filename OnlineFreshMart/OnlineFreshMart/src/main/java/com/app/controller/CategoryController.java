package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.Category;
import com.app.service.CategoryService;

@RestController
@RequestMapping("api/category")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Endpoint to get all categories
    @GetMapping("all")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories(); // Call the service to retrieve all categories
        return new ResponseEntity<>(categories, HttpStatus.OK); // Return categories with OK status
    }

    // Endpoint to add a new category
    @PostMapping("add")
    public ResponseEntity<?> add(@RequestBody Category category) {
        if (categoryService.addCategory(category)) { // Call the service to add the category
            return new ResponseEntity<>(category, HttpStatus.OK); // Return the added category with OK status
        } else {
            return new ResponseEntity<>("Failed to add category!", HttpStatus.INTERNAL_SERVER_ERROR);
            // Return an error message with Internal Server Error status if category addition fails
        }
    }
}
