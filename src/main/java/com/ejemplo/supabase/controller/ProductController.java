package com.ejemplo.supabase.controller;

import com.ejemplo.supabase.model.Product;
import com.ejemplo.supabase.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    public record CreateProductRequest(String name, BigDecimal price, Integer stock, Long categoryId,
            Set<String> tags) {
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody CreateProductRequest req) {
        Product created = productService.create(req.name(), req.price(), req.stock(), req.categoryId(), req.tags());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
