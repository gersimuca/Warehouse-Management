package com.gersimuca.Warehouse.Management.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequest {

    @NotEmpty(message = "ID cannot be empty or null")
    @JsonProperty("item_id")
    private Long ItemId;

    @NotEmpty(message = "Quantity cannot be empty or null")
    private Integer quantity;

    @NotEmpty(message = "Name cannot be empty or null")
    private String name;

}
