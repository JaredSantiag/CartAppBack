package com.jaredsantiag.backendcartapp.services;

import com.jaredsantiag.backendcartapp.models.entities.Order;
import com.jaredsantiag.backendcartapp.models.entities.User;
import com.jaredsantiag.backendcartapp.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Order> findByUser(User user) {
        return repository.findByUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Order save(Order order) {
        return repository.save(order);
    }

    @Override
    public void remove(Long id) {
        repository.deleteById(id);
    }
}
