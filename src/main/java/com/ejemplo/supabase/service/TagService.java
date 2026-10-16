package com.ejemplo.supabase.service;

import com.ejemplo.supabase.model.Product;
import com.ejemplo.supabase.model.Tag;
import com.ejemplo.supabase.repository.ProductRepository;
import com.ejemplo.supabase.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final ProductRepository productRepository;

    public TagService(TagRepository tagRepository, ProductRepository productRepository) {
        this.tagRepository = tagRepository;
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Tag getById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag no encontrado: " + id));
    }

    @Transactional(readOnly = true)
    public List<Product> getProductsByTag(String tagName) {
        return productRepository.findByTags_Name(tagName);
    }

    @Transactional
    public Tag create(String name) {
        if (tagRepository.findByName(name).isPresent()) {
            throw new RuntimeException("Tag ya existe: " + name);
        }
        Tag tag = Tag.builder().name(name).build();
        return tagRepository.save(tag);
    }

    @Transactional
    public Tag addProducts(Long tagId, Set<Long> productIds) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag no encontrado: " + tagId));

        for (Long productId : productIds) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + productId));
            product.addTag(tag);
            productRepository.save(product);
        }

        return tag;
    }
}
