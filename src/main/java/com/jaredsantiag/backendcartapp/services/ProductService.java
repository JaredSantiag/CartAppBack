package com.jaredsantiag.backendcartapp.services;

import com.jaredsantiag.backendcartapp.models.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();
    Page<Product> findAll(Pageable pageable);
    Optional<Product> findById(Long id);
    Product save(Product product);
    Optional<Product> update(Product product, Long id);
    void remove(Long id);
}
