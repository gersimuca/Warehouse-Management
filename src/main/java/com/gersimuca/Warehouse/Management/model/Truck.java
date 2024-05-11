package com.gersimuca.Warehouse.Management.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "truck")
public class Truck {

    @Id
    @Column(unique = true, name = "chassis_number")
    private String chassisNumber;

    @Column(unique = true, name = "license_plate")
    private String licensePlate;

    @Column(name = "capacity")
    private Integer capacity;
}
