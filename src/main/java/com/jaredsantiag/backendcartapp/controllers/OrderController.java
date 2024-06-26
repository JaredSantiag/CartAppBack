package com.jaredsantiag.backendcartapp.controllers;

import com.jaredsantiag.backendcartapp.helpers.ValidationHelper;
import com.jaredsantiag.backendcartapp.models.entities.Item;
import com.jaredsantiag.backendcartapp.models.entities.Order;
import com.jaredsantiag.backendcartapp.models.entities.Product;
import com.jaredsantiag.backendcartapp.models.entities.User;
import com.jaredsantiag.backendcartapp.services.*;
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
    private OrderService service;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PaymentMethodService paymentMethodService;

    @Autowired
    private ItemService itemService;

    @GetMapping()
    public List<Order> list(Principal principal){
        String username = principal.getName();
        Optional<User> user = userService.findByUsername(username);
        return service.findByUser(user.orElseThrow());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(Principal principal, @PathVariable Long id){
        String username = principal.getName();
        Optional<Order> orderOptional = service.findById(id);
        Optional<User> user = userService.findByUsername(username);

        if(orderOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        if(!orderOptional.get().getUser().equals(user.get())) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(orderOptional.orElseThrow());
    }

    @PostMapping
    public ResponseEntity<?> create(Principal principal, @RequestBody Order orderRequest){
        String username = principal.getName();
        Optional<User> user = userService.findByUsername(username);
        List<Item> items;

        if(orderRequest.getItems()==null || orderRequest.getItems().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid order");
        }
        items = orderRequest.getItems();

        if(addressService.findById(orderRequest.getAddress().getId()).isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid address");
        }

        if(paymentMethodService.findById(orderRequest.getPaymentMethod().getId()).isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid payment method");
        }

        Order order = new Order();
        order.setOrderDate(new Date());
        order.setUser(user.orElseThrow());
        order.setAddress(orderRequest.getAddress());
        order.setPaymentMethod(orderRequest.getPaymentMethod());
        service.save(order);

        items.forEach((i) -> i.setOrder(order));
        order.setItems(items);
        service.save(order);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(Principal principal, @PathVariable Long id){
        String username = principal.getName();
        Optional<Order> orderOptional = service.findById(id);
        Optional<User> user = userService.findByUsername(username);

        if(orderOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        if(!orderOptional.get().getUser().equals(user.get())) {
            return ResponseEntity.notFound().build();
        }

        service.remove(id);
        return ResponseEntity.noContent().build();
    }
}
