package com.jaredsantiag.backendcartapp.services;

import java.util.List;
import java.util.Optional;

import com.jaredsantiag.backendcartapp.models.dto.UserDTO;
import com.jaredsantiag.backendcartapp.models.entities.User;
import com.jaredsantiag.backendcartapp.models.request.UserRequest;

public interface UserService {
    
    List<UserDTO> findAll();

    Optional<UserDTO> findById(Long id);

    UserDTO save(User user);
    Optional<UserDTO> update(UserRequest user, Long id);

    void remove(Long id);
}
