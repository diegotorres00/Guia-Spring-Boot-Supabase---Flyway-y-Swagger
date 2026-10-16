package com.ejemplo.supabase.service;

import com.ejemplo.supabase.model.Category;
import com.ejemplo.supabase.model.Product;
import com.ejemplo.supabase.repository.CategoryRepository;
import com.ejemplo.supabase.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + id));
    }

    @Transactional(readOnly = true)
    public List<Product> getProductsByCategory(Long id) {
        return productRepository.findByCategoryId(id);
    }

    @Transactional
    public Category create(String name) {
        Category category = Category.builder().name(name).build();
        return categoryRepository.save(category);
    }

    @Transactional
    public Category addProducts(Long categoryId, Set<Long> productIds) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + categoryId));

        for (Long productId : productIds) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + productId));
            category.addProduct(product);
        }

        return categoryRepository.save(category);
    }
}
