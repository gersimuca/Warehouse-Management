package com.gersimuca.Warehouse.Management.util;

import com.gersimuca.Warehouse.Management.model.Delivery;
import com.gersimuca.Warehouse.Management.model.Order;
import com.gersimuca.Warehouse.Management.model.Truck;

import java.time.LocalDate;

public class DeliveryUtil {
    public static Delivery createDelivery(LocalDate date, Order order, Truck truck){
        return Delivery.builder()
                .deliveryDate(date)
                .order(order)
                .truck(truck)
                .build();
    }
}
