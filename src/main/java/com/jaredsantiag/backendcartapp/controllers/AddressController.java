package com.jaredsantiag.backendcartapp.controllers;

import com.jaredsantiag.backendcartapp.models.entities.Address;
import com.jaredsantiag.backendcartapp.models.entities.User;
import com.jaredsantiag.backendcartapp.services.AddressService;
import com.jaredsantiag.backendcartapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/address")
@CrossOrigin(origins = "*")
public class AddressController {
    @Autowired
    private AddressService service;

    @Autowired
    private UserService userService;

    @GetMapping()
    public List<Address> list(Principal principal){
        String username = principal.getName();
        Optional<User> user = userService.findByUsername(username);
        List<Address> addresses = service.findByUser(user.orElseThrow());
        return addresses;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(Principal principal, @PathVariable Long id){
        String username = principal.getName();
        Optional<User> user = userService.findByUsername(username);
        Optional<Address> address = service.findById(id);

        if(address.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Address addressObj = address.get();
        if(!addressObj.getUser().equals(user)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(address);
    }

    @PostMapping()
    public ResponseEntity<?> create(Principal principal, @RequestBody Address request){
        String username = principal.getName();
        Optional<User> user = userService.findByUsername(username);

        Address address = new Address();
        address.setUser(user.orElseThrow());
        address.setStreet(request.getStreet());
        address.setNumber(request.getNumber());
        address.setSuburb(request.getSuburb());
        address.setPostCode(request.getPostCode());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setCountry(request.getCountry());

        service.save(address);
        return ResponseEntity.status(HttpStatus.CREATED).body(address);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(Principal principal, @PathVariable Long id){
        String username = principal.getName();
        Optional<User> user = userService.findByUsername(username);
        Optional<Address> address = service.findById(id);

        if(address.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Address addressObj = address.get();
        if(!addressObj.getUser().equals(user)) {
            return ResponseEntity.noContent().build();
        }

        service.remove(id);
        return ResponseEntity.noContent().build();
    }
}
