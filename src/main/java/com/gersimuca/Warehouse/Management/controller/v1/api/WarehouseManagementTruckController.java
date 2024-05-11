package com.gersimuca.Warehouse.Management.controller.v1.api;

import com.gersimuca.Warehouse.Management.domain.Response;
import com.gersimuca.Warehouse.Management.dto.request.TruckRequest;
import com.gersimuca.Warehouse.Management.service.TruckService;
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
import java.util.Map;

@RestController
@RequestMapping("/api/v1/management/trucks")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('WAREHOUSE_MANAGER')")
@Tag(name = "Manager Operation on Truck")
public class WarehouseManagementTruckController {

    private static final Logger logger = LogManager.getLogger(WarehouseManagementTruckController.class);

    private final TruckService truckService;

    @GetMapping
    public ResponseEntity<Response> getAllTrucks(HttpServletRequest request) {
        Map<?, ?> data = truckService.getAllTrucks();
        String successMessage = "Trucks retrieved successfully!";
        Response responseBody = RequestUtil.getResponse(request, data, successMessage, "", HttpStatus.OK);

        logger.info("All trucks retrieved");

        return ResponseEntity.ok().body(responseBody);
    }

    @GetMapping("/{chassisNumber}")
    public ResponseEntity<Response> getTruckByChassisNumber(@PathVariable String chassisNumber, HttpServletRequest request) {
        Map<?, ?> data = truckService.getTruckByChassisNumber(chassisNumber);
        String successMessage = "Truck retrieved successfully!";
        Response responseBody = RequestUtil.getResponse(request, data, successMessage, "", HttpStatus.OK);

        logger.info("Truck retrieved by chassis number: {}", chassisNumber);

        return ResponseEntity.ok().body(responseBody);
    }

    @PostMapping
    public ResponseEntity<Response> createTruck(@RequestBody TruckRequest truckRequest, HttpServletRequest request) {
        truckService.createTruck(truckRequest);
        String successMessage = "Truck created successfully!";

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .build()
                .toUri();

        Response responseBody = RequestUtil.getResponse(request, Collections.emptyMap(), successMessage, "", HttpStatus.OK);

        logger.info("Truck created with chassis number: {}", truckRequest.getChassisNumber());

        return ResponseEntity.created(location).body(responseBody);
    }

    @PutMapping("/{chassisNumber}")
    public ResponseEntity<Response> updateTruck(@PathVariable String chassisNumber, @RequestBody TruckRequest truckRequest, HttpServletRequest request) {
        truckService.updateTruck(chassisNumber, truckRequest);
        String successMessage = "Truck updated successfully!";
        Response responseBody = RequestUtil.getResponse(request, Collections.emptyMap(), successMessage, "", HttpStatus.OK);

        logger.info("Truck updated with chassis number: {}", chassisNumber);

        return ResponseEntity.ok().body(responseBody);
    }

    @DeleteMapping("/{chassisNumber}")
    public ResponseEntity<Response> deleteItem(@PathVariable String chassisNumber, HttpServletRequest request) {
        truckService.deleteTruck(chassisNumber);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{chassisNumber}")
                .buildAndExpand(chassisNumber)
                .toUri();

        logger.info("Truck deleted with chassis number: {}", chassisNumber);

        return ResponseEntity.noContent().location(location).build();
    }
}
