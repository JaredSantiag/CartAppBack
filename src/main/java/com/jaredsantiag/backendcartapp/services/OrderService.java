package com.jaredsantiag.backendcartapp.services;

import com.jaredsantiag.backendcartapp.models.entities.Order;
import com.jaredsantiag.backendcartapp.models.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> findByUser(User user);
    Optional<Order> findById(Long Id);
    Order save(Order order);
    void remove(Long id);
}
