package com.jaredsantiag.backendcartapp.repositories;

import com.jaredsantiag.backendcartapp.models.entities.Order;
import com.jaredsantiag.backendcartapp.models.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByUser(User user);
    Optional<Order> findById(Long id);
}
