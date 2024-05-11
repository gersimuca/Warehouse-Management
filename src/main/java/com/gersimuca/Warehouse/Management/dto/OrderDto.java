package com.gersimuca.Warehouse.Management.dto;

import com.gersimuca.Warehouse.Management.enumeration.Status;

import java.time.LocalDate;

public record OrderDto (Long orderId, LocalDate submittedDate, Status status){
}
