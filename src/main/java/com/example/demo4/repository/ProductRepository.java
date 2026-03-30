package com.example.demo4.repository;

import com.example.demo4.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    Page<Product> findByCategoryName(String categoryName, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCaseAndCategoryName(String keyword, String categoryName, Pageable pageable);

    @Query("select distinct p.categoryName from Product p where p.categoryName is not null and p.categoryName <> ''")
    List<String> findDistinctCategoryNames();
}