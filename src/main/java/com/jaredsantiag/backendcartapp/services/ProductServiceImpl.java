package com.jaredsantiag.backendcartapp.services;

import com.jaredsantiag.backendcartapp.models.entities.Product;
import com.jaredsantiag.backendcartapp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return (List<Product>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional()
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public Optional<Product> update(Product product, Long id) {
        Optional<Product> o = this.findById(id);
        Product productOpttional = null;
        if(o.isPresent()){
            Product productDB = o.orElseThrow();
            productDB.setName(product.getName());
            productDB.setDescription(product.getDescription());
            productDB.setPrice(product.getPrice());
            productOpttional = this.save(productDB);
        }
        return Optional.ofNullable(productOpttional);
    }

    @Override
    @Transactional()
    public void remove(Long id) {
        repository.deleteById(id);
    }

}
