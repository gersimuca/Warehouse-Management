package com.gersimuca.Warehouse.Management.repository;

import com.gersimuca.Warehouse.Management.model.OrderItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {
}
