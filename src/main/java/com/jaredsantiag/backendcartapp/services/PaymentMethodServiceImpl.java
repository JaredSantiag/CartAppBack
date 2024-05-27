package com.jaredsantiag.backendcartapp.services;

import com.jaredsantiag.backendcartapp.models.entities.PaymentMethod;
import com.jaredsantiag.backendcartapp.models.entities.User;
import com.jaredsantiag.backendcartapp.repositories.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService{

    @Autowired
    private PaymentMethodRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentMethod> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentMethod> findByUser(User user) {
        return repository.findByUser(user);
    }

    @Override
    @Transactional()
    public PaymentMethod save(PaymentMethod paymentMethod) {
        return repository.save(paymentMethod);
    }

    @Override
    @Transactional()
    public void remove(Long id) {
        repository.deleteById(id);
    }
}
