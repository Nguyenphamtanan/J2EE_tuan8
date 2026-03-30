package com.example.demo4.service;

import com.example.demo4.model.Product;
import com.example.demo4.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Page<Product> searchProducts(String keyword, String category, String sort, int page, int size) {
        Sort sortObj = Sort.unsorted();

        if ("price_asc".equals(sort)) {
            sortObj = Sort.by("price").ascending();
        } else if ("price_desc".equals(sort)) {
            sortObj = Sort.by("price").descending();
        }

        Pageable pageable = PageRequest.of(page, size, sortObj);

        boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
        boolean hasCategory = category != null && !category.trim().isEmpty();

        if (hasKeyword && hasCategory) {
            return productRepository.findByNameContainingIgnoreCaseAndCategoryName(
                    keyword.trim(), category.trim(), pageable
            );
        } else if (hasKeyword) {
            return productRepository.findByNameContainingIgnoreCase(keyword.trim(), pageable);
        } else if (hasCategory) {
            return productRepository.findByCategoryName(category.trim(), pageable);
        } else {
            return productRepository.findAll(pageable);
        }
    }

    public List<String> getAllCategories() {
        return productRepository.findDistinctCategoryNames();
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}