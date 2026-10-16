package com.ejemplo.supabase.repository;

import com.ejemplo.supabase.model.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = { "category", "tags" })
    List<Product> findAll();

    @Query("SELECT DISTINCT p FROM Product p " +
            "LEFT JOIN FETCH p.category " +
            "LEFT JOIN FETCH p.tags " +
            "WHERE p.id = :id")
    Optional<Product> findByIdWithDetails(@Param("id") Long id);

    @EntityGraph(attributePaths = { "category", "tags" })
    List<Product> findByCategoryId(Long categoryId);

    @EntityGraph(attributePaths = { "category", "tags" })
    List<Product> findByTags_Name(String tagName);
}
