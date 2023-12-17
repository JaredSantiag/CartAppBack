package com.jaredsantiag.backendcartapp.repositories;

import com.jaredsantiag.backendcartapp.models.entities.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
