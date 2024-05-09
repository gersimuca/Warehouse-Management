package com.gersimuca.Warehouse.Management.repository;

import com.gersimuca.Warehouse.Management.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long>{
}
