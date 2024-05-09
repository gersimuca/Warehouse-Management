package com.gersimuca.Warehouse.Management.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Truck {

    @Id
    @Column(unique = true)
    private String chassisNumber;

    @Column(unique = true)
    private String licensePlate;
}
