package com.gersimuca.Warehouse.Management.repository;

import com.gersimuca.Warehouse.Management.model.Delivery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends CrudRepository<Delivery, Long> {
}
