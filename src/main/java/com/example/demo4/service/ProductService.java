package com.example.demo4.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo4.model.Product;
import com.example.demo4.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<Product> getAllProducts(){
        return repository.findAll();
    }

    public void save(Product product){
        repository.save(product);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }
    public Product getProductById(Long id){
        return repository.findById(id).orElse(null);
    }
}