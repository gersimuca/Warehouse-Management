package com.gersimuca.Warehouse.Management.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gersimuca.Warehouse.Management.enumeration.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderRequest {
    @NotEmpty(message = "Status cannot be empty or null")
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotEmpty(message = "DeadlineDate cannot be empty or null")
    private LocalDate deadlineDate;

    private List<ItemRequest> itemRequestList;
}
