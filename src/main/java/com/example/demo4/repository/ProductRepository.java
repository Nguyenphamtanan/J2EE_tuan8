package com.example.demo4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo4.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}