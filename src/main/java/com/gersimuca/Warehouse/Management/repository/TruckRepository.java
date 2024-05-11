package com.gersimuca.Warehouse.Management.repository;

import com.gersimuca.Warehouse.Management.model.Truck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TruckRepository extends JpaRepository<Truck, String> {
    Optional<Truck> findByChassisNumber(String chassisNumber);
    Optional<List<Truck>> findAllByChassisNumberIn(Collection<String> chassisNumber);
}
