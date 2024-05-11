package com.gersimuca.Warehouse.Management.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemRequest {

    @JsonProperty("item_id")
    private Long ItemId;

    @NotEmpty(message = "Quantity cannot be empty or null")
    private Integer quantity;

    @NotEmpty(message = "Name cannot be empty or null")
    private String name;

    @JsonProperty("unit_price")
    private double unitPrice;

}
