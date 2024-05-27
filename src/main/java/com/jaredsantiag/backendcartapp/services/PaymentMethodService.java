package com.jaredsantiag.backendcartapp.services;

import com.jaredsantiag.backendcartapp.models.entities.PaymentMethod;
import com.jaredsantiag.backendcartapp.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface PaymentMethodService {
    Optional<PaymentMethod> findById(Long id);
    List<PaymentMethod> findByUser(User user);
    PaymentMethod save(PaymentMethod paymentMethod);
    void remove(Long id);
}
