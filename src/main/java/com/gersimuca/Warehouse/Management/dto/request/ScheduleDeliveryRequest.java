package com.gersimuca.Warehouse.Management.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScheduleDeliveryRequest {
    private LocalDate deliveryDate;
    private List<String> truckChassisNumbers;
    private Long orderId;
}
