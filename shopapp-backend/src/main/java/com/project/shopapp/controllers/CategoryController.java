package com.project.shopapp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

    @PostMapping("")
    public ResponseEntity<String> createCategory() {
        return ResponseEntity.ok("Category created successfully.");
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok("Category updated successfully. categoryId = " + categoryId);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok("Category deleted successfully. categoryId = " + categoryId);
    }

    @GetMapping("")
    public ResponseEntity<String> getAllCategories() {
        return ResponseEntity.ok("Categories retrieved successfully.");
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<String> getCategoryById(@PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok("Category retrieved successfully. categoryId = " + categoryId);
    }

}
