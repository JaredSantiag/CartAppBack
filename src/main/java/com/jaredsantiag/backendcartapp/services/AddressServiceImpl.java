package com.jaredsantiag.backendcartapp.services;

import com.jaredsantiag.backendcartapp.models.entities.Address;
import com.jaredsantiag.backendcartapp.models.entities.User;
import com.jaredsantiag.backendcartapp.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements  AddressService{

    @Autowired
    private AddressRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Address> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Address> findByUser(User user) {
        return repository.findByUser(user);
    }

    @Override
    @Transactional()
    public Address save(Address address) {
        return repository.save(address);
    }

    @Override
    @Transactional()
    public void remove(Long id) {
        repository.deleteById(id);
    }
}
