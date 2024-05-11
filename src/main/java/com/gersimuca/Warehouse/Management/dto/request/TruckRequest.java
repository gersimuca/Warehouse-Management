package com.gersimuca.Warehouse.Management.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TruckRequest {
    @NotEmpty(message = "Chassis number cannot be empty or null")
    private String chassisNumber;

    @NotEmpty(message = "License plate cannot be empty or null")
    private String licensePlate;

    @NotEmpty(message = "Capacity cannot be empty or null")
    private Integer capacity;
}
