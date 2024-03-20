package com.jaredsantiag.backendcartapp.services;

import com.jaredsantiag.backendcartapp.models.entities.Item;
import com.jaredsantiag.backendcartapp.models.entities.Order;

import java.util.List;

public interface ItemService {
    List<Item> findByOrder(Order order);

    Item save(Item item);
}
