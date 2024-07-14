package com.jaredsantiag.backendcartapp.services;

import com.jaredsantiag.backendcartapp.models.entities.Address;
import com.jaredsantiag.backendcartapp.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface AddressService {
    Optional<Address> findById(Long id);
    List<Address> findByUser(User user);
    Address save(Address address);
    Optional update(Address address, Long id);
    void remove(Long id);
}
