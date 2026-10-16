package com.ejemplo.supabase.controller;

import com.ejemplo.supabase.model.Product;
import com.ejemplo.supabase.model.Tag;
import com.ejemplo.supabase.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<Tag>> getAll() {
        return ResponseEntity.ok(tagService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.getById(id));
    }

    @GetMapping("/{name}/products")
    public ResponseEntity<List<Product>> getProductsByTag(@PathVariable String name) {
        return ResponseEntity.ok(tagService.getProductsByTag(name));
    }

    public record CreateTagRequest(String name) {
    }

    @PostMapping
    public ResponseEntity<Tag> create(@RequestBody CreateTagRequest req) {
        Tag created = tagService.create(req.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}/products")
    public ResponseEntity<Tag> addProducts(@PathVariable Long id, @RequestBody Set<Long> productIds) {
        Tag updated = tagService.addProducts(id, productIds);
        return ResponseEntity.ok(updated);
    }
}
