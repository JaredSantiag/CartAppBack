package com.jaredsantiag.backendcartapp.controllers;

import com.jaredsantiag.backendcartapp.models.entities.PaymentMethod;
import com.jaredsantiag.backendcartapp.models.entities.User;
import com.jaredsantiag.backendcartapp.services.PaymentMethodService;
import com.jaredsantiag.backendcartapp.services.UserService;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paymentmethods")
@CrossOrigin(origins = "*")
public class PaymentMethodController {

    @Autowired
    private PaymentMethodService service;

    @Autowired
    private UserService userService;

    @Autowired
    private StringEncryptor stringEncryptor;

    @GetMapping()
    public List<PaymentMethod> list(Principal principal){
        String username = principal.getName();
        Optional<User> user = userService.findByUsername(username);
        List<PaymentMethod> paymentMethods = service.findByUser(user.orElseThrow());
        paymentMethods.forEach((p) -> {
            p.setCardNumber(stringEncryptor.decrypt(p.getCardNumber()));
            p.setMonthExpiration(stringEncryptor.decrypt(p.getMonthExpiration()));
            p.setYearExpiration(stringEncryptor.decrypt(p.getYearExpiration()));
            p.setSecurityCode(stringEncryptor.decrypt(p.getSecurityCode()));
        });
        return paymentMethods;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Optional<PaymentMethod> paymentMethod = service.findById(id);
        if(paymentMethod.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(paymentMethod.orElseThrow());
    }

    @PostMapping()
    public ResponseEntity<?> create(Principal principal, @RequestBody PaymentMethod request){
        String username = principal.getName();
        Optional<User> user = userService.findByUsername(username);

        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setUser(user.orElseThrow());
        paymentMethod.setCardNumber(stringEncryptor.encrypt(request.getCardNumber()));
        paymentMethod.setMonthExpiration(stringEncryptor.encrypt(request.getMonthExpiration()));
        paymentMethod.setYearExpiration(stringEncryptor.encrypt(request.getYearExpiration()));
        paymentMethod.setSecurityCode(stringEncryptor.encrypt(request.getYearExpiration()));

        service.save(paymentMethod);
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentMethod);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id){
        Optional<PaymentMethod> paymentMethod =  service.findById(id);
        if(paymentMethod.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        service.remove(id);
        return ResponseEntity.noContent().build();
    }
}
