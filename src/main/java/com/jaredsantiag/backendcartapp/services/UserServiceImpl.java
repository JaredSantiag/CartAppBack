package com.jaredsantiag.backendcartapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.jaredsantiag.backendcartapp.models.IUser;
import com.jaredsantiag.backendcartapp.models.dto.UserDTO;
import com.jaredsantiag.backendcartapp.models.dto.mapper.DtoMapperUser;
import com.jaredsantiag.backendcartapp.models.entities.Role;
import com.jaredsantiag.backendcartapp.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaredsantiag.backendcartapp.models.entities.User;
import com.jaredsantiag.backendcartapp.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        List<User> users = (List<User>) repository.findAll();

        return users
                .stream()
                .map(u -> DtoMapperUser.builder().setUser(u).build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO> findById(Long id) {
        return repository.findById(id).map(u -> DtoMapperUser
                .builder()
                .setUser(u)
                .build());
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDTO save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(getRoles(user));
        return DtoMapperUser.builder().setUser(repository.save(user)).build();
    }

    @Override
    @Transactional
    public Optional<UserDTO> update(User user, Long id) {
        Optional<User> o = repository.findById(id);
        User userOptional = null;
        if (o.isPresent()) {
            User userDb = o.orElseThrow();
            //userDb.setRoles(getRoles(user));
            userDb.setUsername(user.getUsername());
            userDb.setEmail(user.getEmail());
            userDb.setPhoneNumber(user.getPhoneNumber());
            if(!user.getPassword().isEmpty()){
                userDb.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            userOptional = repository.save(userDb);
        }
        return Optional.ofNullable(DtoMapperUser.builder().setUser(userOptional).build());
    }

    @Override
    @Transactional
    public void remove(Long id) {
        repository.deleteById(id);
    }

    private List<Role> getRoles(IUser user){
        Optional<Role> ou = roleRepository.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();

        if(ou.isPresent()){
            roles.add(ou.orElseThrow());
        }

        if(user.isAdmin()){
            Optional<Role> oa = roleRepository.findByName("ROLE_ADMIN");
            if(oa.isPresent()){
                roles.add(oa.orElseThrow());
            }
        }

        return roles;
    }
}
