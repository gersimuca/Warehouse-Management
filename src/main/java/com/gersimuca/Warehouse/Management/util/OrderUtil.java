package com.gersimuca.Warehouse.Management.util;

import com.gersimuca.Warehouse.Management.enumeration.Status;
import com.gersimuca.Warehouse.Management.model.Order;
import com.gersimuca.Warehouse.Management.model.User;

import java.time.LocalDate;

public class OrderUtil {
    public static Order createOrder(Status status, LocalDate date, User user){
        return Order.builder()
                .status(status)
                .deadlineDate(date)
                .user(user)
                .build();
    }
}
