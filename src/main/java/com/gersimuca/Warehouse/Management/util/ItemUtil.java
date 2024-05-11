package com.gersimuca.Warehouse.Management.util;

import com.gersimuca.Warehouse.Management.model.Item;

public class ItemUtil {
    public static Item createItem(String name, double price, int quantity){
        return Item.builder()
                .itemName(name)
                .unitPrice(price)
                .quantity(quantity)
                .build();
    }
}
