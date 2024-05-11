package com.gersimuca.Warehouse.Management.dto.mapper;

import com.gersimuca.Warehouse.Management.dto.OrderDto;
import com.gersimuca.Warehouse.Management.model.Order;

import java.util.function.Function;

public class OrderDtoMapper implements Function<Order, OrderDto> {

    @Override
    public OrderDto apply(Order order) {
        return new OrderDto(
                order.getOrderId(),
                order.getSubmittedDate(),
                order.getStatus()
        );
    }
}
