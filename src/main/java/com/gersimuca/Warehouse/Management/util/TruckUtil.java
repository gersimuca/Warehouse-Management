package com.gersimuca.Warehouse.Management.util;

import com.gersimuca.Warehouse.Management.model.Truck;

public class TruckUtil {
    public static Truck createTruck(String chassisNumber, String licensePlate, int capacity){
        return Truck.builder()
                .chassisNumber(chassisNumber)
                .licensePlate(licensePlate)
                .capacity(capacity)
                .build();
    }
}
