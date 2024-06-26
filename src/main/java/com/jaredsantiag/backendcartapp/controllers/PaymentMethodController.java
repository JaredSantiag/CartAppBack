package com.jaredsantiag.backendcartapp.controllers;

import com.jaredsantiag.backendcartapp.helpers.EncriptorHelper;
import com.jaredsantiag.backendcartapp.models.entities.PaymentMethod;
import com.jaredsantiag.backendcartapp.models.entities.User;
import com.jaredsantiag.backendcartapp.services.PaymentMethodService;
import com.jaredsantiag.backendcartapp.services.UserService;
import io.micrometer.observation.Observation;
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

        EncriptorHelper encriptorHelper = new EncriptorHelper(stringEncryptor);
        paymentMethods.forEach(encriptorHelper::decryptPaymentMethod);

        return paymentMethods;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(Principal principal, @PathVariable Long id){
        String username = principal.getName();
        Optional<PaymentMethod> paymentMethod = service.findById(id);
        Optional<User> user = userService.findByUsername(username);

        if(paymentMethod.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        if(!paymentMethod.get().getUser().equals(user.get())) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(paymentMethod.orElseThrow());
    }

    @PostMapping()
    public ResponseEntity<?> create(Principal principal, @RequestBody PaymentMethod request){
        String username = principal.getName();
        Optional<User> user = userService.findByUsername(username);

        EncriptorHelper encriptorHelper = new EncriptorHelper(stringEncryptor);

        PaymentMethod paymentMethod = encriptorHelper.encryptPaymentMethod(request);
        paymentMethod.setUser(user.orElseThrow());

        service.save(paymentMethod);
        return ResponseEntity.status(HttpStatus.CREATED).body(encriptorHelper.decryptPaymentMethod(paymentMethod));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(Principal principal, @PathVariable Long id){
        String username = principal.getName();
        Optional<PaymentMethod> paymentMethod =  service.findById(id);
        Optional<User> user = userService.findByUsername(username);

        if(paymentMethod.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        if(!paymentMethod.get().getUser().equals(user.get())) {
            return ResponseEntity.notFound().build();
        }

        service.remove(id);
        return ResponseEntity.noContent().build();
    }
}
