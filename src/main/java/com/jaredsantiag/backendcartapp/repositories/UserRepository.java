package com.jaredsantiag.backendcartapp.repositories;

import org.springframework.data.repository.CrudRepository;

import com.jaredsantiag.backendcartapp.models.entities.User;

public interface UserRepository
        extends CrudRepository<User, Long> {

}
