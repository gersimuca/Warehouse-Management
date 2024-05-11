package com.gersimuca.Warehouse.Management.util;

import com.gersimuca.Warehouse.Management.model.Item;
import com.gersimuca.Warehouse.Management.model.Order;
import com.gersimuca.Warehouse.Management.model.OrderItem;

public class OrderItemUtil {

    public static OrderItem createOrderItem(Item item, Order order, int quantity){
        return OrderItem.builder()
                .item(item)
                .order(order)
                .quantity(quantity)
                .build();
    }
}
