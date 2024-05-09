package com.gersimuca.Warehouse.Management.repository;

import com.gersimuca.Warehouse.Management.model.Truck;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TruckRepository extends CrudRepository<Truck, String> {
}
