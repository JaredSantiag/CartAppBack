package com.jaredsantiag.backendcartapp.controllers;

import com.jaredsantiag.backendcartapp.models.entities.Order;
import com.jaredsantiag.backendcartapp.models.entities.User;
import com.jaredsantiag.backendcartapp.services.OrderService;
import com.jaredsantiag.backendcartapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*")
public class OrderController {
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService service;

    @GetMapping()
    public List<Order> list(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<User> user = userService.findByUsername(userDetails.getUsername());
        return service.findByUser(user.orElseThrow());
    }
}
