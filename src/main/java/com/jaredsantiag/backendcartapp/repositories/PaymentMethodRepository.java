package com.jaredsantiag.backendcartapp.repositories;

import com.jaredsantiag.backendcartapp.models.entities.PaymentMethod;
import com.jaredsantiag.backendcartapp.models.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PaymentMethodRepository extends CrudRepository<PaymentMethod, Long> {
    List<PaymentMethod> findByUser(User user);
}
