package com.jaredsantiag.backendcartapp.services;

import com.jaredsantiag.backendcartapp.models.entities.Address;
import com.jaredsantiag.backendcartapp.models.entities.User;
import com.jaredsantiag.backendcartapp.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements  AddressService{

    @Autowired
    private AddressRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Address> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Address> findByUser(User user) {
        return repository.findByUser(user);
    }

    @Override
    @Transactional()
    public Address save(Address address) {
        return repository.save(address);
    }

    @Override
    public Optional<Address> update(Address address, Long id) {
        Optional<Address> o = this.findById(id);
        Address adreessOptional = null;

        if(o.isPresent()){
            Address addressDB = o.orElseThrow();
            addressDB.setStreet(address.getStreet());
            addressDB.setNumber(address.getNumber());
            addressDB.setSuburb(address.getSuburb());
            addressDB.setPostCode(address.getPostCode());
            addressDB.setCity(address.getCity());
            addressDB.setState(address.getState());
            addressDB.setCountry(address.getCountry());
            adreessOptional = this.save(addressDB);
        }
        return Optional.ofNullable(adreessOptional);
    }

    @Override
    @Transactional()
    public void remove(Long id) {
        repository.deleteById(id);
    }
}
