package com.jaredsantiag.backendcartapp.repositories;

import org.springframework.data.repository.CrudRepository;

import com.jaredsantiag.backendcartapp.models.entities.User;

import java.util.Optional;

public interface UserRepository
        extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
