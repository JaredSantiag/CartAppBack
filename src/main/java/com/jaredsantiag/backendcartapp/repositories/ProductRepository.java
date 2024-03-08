package com.jaredsantiag.backendcartapp.repositories;

import com.jaredsantiag.backendcartapp.models.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository
        extends CrudRepository<Product, Long> {

    Page<Product> findAll(Pageable pageable);
}
