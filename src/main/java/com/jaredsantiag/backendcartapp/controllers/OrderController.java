package com.jaredsantiag.backendcartapp.controllers;

import com.jaredsantiag.backendcartapp.helpers.ValidationHelper;
import com.jaredsantiag.backendcartapp.models.entities.Order;
import com.jaredsantiag.backendcartapp.models.entities.Product;
import com.jaredsantiag.backendcartapp.models.entities.User;
import com.jaredsantiag.backendcartapp.services.OrderService;
import com.jaredsantiag.backendcartapp.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
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
    public List<Order> list(Principal principal){
        String username = principal.getName();
        Optional<User> user = userService.findByUsername(username);
        return service.findByUser(user.orElseThrow());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Optional<Order> orderOptional = service.findById(id);
        if(orderOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderOptional.orElseThrow());
    }

    @PostMapping
    public ResponseEntity<?> create(Principal principal, @RequestBody Order orderRequest){
        String username = principal.getName();
        Optional<User> user = userService.findByUsername(username);

        Order order = new Order();
        order.setOrderDate(new Date());
        order.setUser(user.orElseThrow());

        if(orderRequest.getProducts().isEmpty()) {
            return ResponseEntity.badRequest().body("Lista de productos vacia");
        }
        order.setProducts(orderRequest.getProducts());

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(order));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id){
        Optional<Order> orderOptional = service.findById(id);
        if(orderOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        service.remove(id);
        return ResponseEntity.noContent().build();
    }
}
