package com.gersimuca.Warehouse.Management.repository;

import com.gersimuca.Warehouse.Management.model.Delivery;
import com.gersimuca.Warehouse.Management.model.Truck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findAllByTruckAndDeliveryDate(Truck truck, LocalDate deliveryDate);
}
