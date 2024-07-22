package com.jaredsantiag.backendcartapp.services;

import java.util.List;
import java.util.Optional;

import com.jaredsantiag.backendcartapp.models.dto.UserDTO;
import com.jaredsantiag.backendcartapp.models.entities.User;
import com.jaredsantiag.backendcartapp.models.request.UserRequest;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    
    List<UserDTO> findAll();

    Optional<UserDTO> findById(Long id);

    Optional<User> findByUsername(String username);

    UserDTO save(User user);

    @Transactional
    Optional<UserDTO> update(User user, Long id);

    void remove(Long id);
}
