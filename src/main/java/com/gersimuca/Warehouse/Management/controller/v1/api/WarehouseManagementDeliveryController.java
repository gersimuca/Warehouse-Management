package com.gersimuca.Warehouse.Management.controller.v1.api;

import com.gersimuca.Warehouse.Management.domain.Response;
import com.gersimuca.Warehouse.Management.dto.request.ScheduleDeliveryRequest;
import com.gersimuca.Warehouse.Management.service.DeliveryService;
import com.gersimuca.Warehouse.Management.util.request.RequestUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/v1/management/deliveries")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('WAREHOUSE_MANAGER')")
@Tag(name = "Manager Operation on Delivery")
public class WarehouseManagementDeliveryController {

    private static final Logger logger = LogManager.getLogger(WarehouseManagementDeliveryController.class);

    private final DeliveryService deliveryService;

    @PostMapping("/schedule")
    public ResponseEntity<Response> scheduleDelivery(@RequestBody ScheduleDeliveryRequest scheduleDeliveryRequest, HttpServletRequest request) {
        deliveryService.scheduleDelivery(scheduleDeliveryRequest);
        String successMessage = "Delivery scheduled successfully.";
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .build()
                .toUri();

        Response responseBody = RequestUtil.getResponse(request, Collections.emptyMap(), successMessage, "", HttpStatus.CREATED);

        logger.info("Delivery scheduled: {}", scheduleDeliveryRequest);

        return ResponseEntity.created(location).body(responseBody);
    }
}
