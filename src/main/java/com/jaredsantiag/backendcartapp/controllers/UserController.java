package com.jaredsantiag.backendcartapp.controllers;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.jaredsantiag.backendcartapp.helpers.EncriptorHelper;
import com.jaredsantiag.backendcartapp.models.dto.UserDTO;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jaredsantiag.backendcartapp.models.entities.User;
import com.jaredsantiag.backendcartapp.models.request.UserRequest;
import com.jaredsantiag.backendcartapp.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin(originPatterns = "*")
public class UserController {
    
    @Autowired
    private UserService service;

    @Autowired
    private StringEncryptor stringEncryptor;

    @GetMapping("/me")
    public ResponseEntity<?> show(Principal principal) {
        String username = principal.getName();
        Optional<User> userOptional = service.findByUsername(username);

        if (userOptional.isPresent()) {
            EncriptorHelper encriptorHelper = new EncriptorHelper(stringEncryptor);
            userOptional.get().getPaymentMethods().forEach(encriptorHelper::decryptPaymentMethod);

            return ResponseEntity.ok(userOptional.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) {
        if(result.hasErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }

    @PutMapping()
    public ResponseEntity<?> update(Principal principal, @Valid @RequestBody User user, BindingResult result) {
        String username = principal.getName();
        Optional<User> userOptional = service.findByUsername(username);

        if(result.hasErrors()){
            return validation(result);
        }

        Optional<UserDTO> o = service.update(user, userOptional.get().getId());
        
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(o.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping()
    public ResponseEntity<?> remove(Principal principal) {
        String username = principal.getName();
        Optional<User> userOptional = service.findByUsername(username);

        Optional<UserDTO> o = service.findById(userOptional.get().getId());

        if (o.isPresent()) {
            service.remove(o.get().getId());
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.notFound().build();
    }
    
    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
