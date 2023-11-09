package com.jaredsantiag.backendcartapp.services;

import com.jaredsantiag.backendcartapp.models.entities.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

}
