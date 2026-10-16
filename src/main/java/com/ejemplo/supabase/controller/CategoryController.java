package com.ejemplo.supabase.controller;

import com.ejemplo.supabase.model.Category;
import com.ejemplo.supabase.model.Product;
import com.ejemplo.supabase.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getProductsByCategory(id));
    }

    public record CreateCategoryRequest(String name) {
    }

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody CreateCategoryRequest req) {
        Category created = categoryService.create(req.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}/products")
    public ResponseEntity<Category> addProducts(@PathVariable Long id, @RequestBody Set<Long> productIds) {
        Category updated = categoryService.addProducts(id, productIds);
        return ResponseEntity.ok(updated);
    }
}
