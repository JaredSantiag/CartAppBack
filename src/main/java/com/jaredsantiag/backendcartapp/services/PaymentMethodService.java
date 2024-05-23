package com.jaredsantiag.backendcartapp.services;

import com.jaredsantiag.backendcartapp.models.entities.PaymentMethod;
import com.jaredsantiag.backendcartapp.models.entities.User;

import java.util.List;

public interface PaymentMethodService {
    List<PaymentMethod> findByUser(User user);

    PaymentMethod save(PaymentMethod paymentMethod);

    void remove(Long id);
}
