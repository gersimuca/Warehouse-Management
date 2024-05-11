package com.gersimuca.Warehouse.Management.controller.v1.api;

import com.gersimuca.Warehouse.Management.domain.Response;
import com.gersimuca.Warehouse.Management.enumeration.Status;
import com.gersimuca.Warehouse.Management.service.OrderService;
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

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/management/orders")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('WAREHOUSE_MANAGER')")
@Tag(name = "Manager Operation on Order")
public class WarehouseManagementOrderController {

    private static final Logger logger = LogManager.getLogger(WarehouseManagementOrderController.class);

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<Response> getOrdersByStatus(@RequestParam("status") Status status, HttpServletRequest request) {
        Map<?, ?> data = orderService.getOrdersByStatus(status);
        String successMessage = "Orders retrieved successfully!";
        Response responseBody = RequestUtil.getResponse(request, data, successMessage, "", HttpStatus.OK);

        logger.info("Orders retrieved by status: {}", status);

        return ResponseEntity.ok().body(responseBody);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getOrderDetails(@PathVariable Long id, HttpServletRequest request) {
        Map<?, ?> data = orderService.getOrderDetails(id);
        String successMessage = "Order retrieved successfully!";
        Response responseBody = RequestUtil.getResponse(request, data, successMessage, "", HttpStatus.OK);

        logger.info("Order retrieved by id: {}", id);

        return ResponseEntity.ok().body(responseBody);
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<Response> approveOrder(@PathVariable Long id, HttpServletRequest request) {
        orderService.approveOrder(id);
        String successMessage = "Order approved successfully!";
        Response responseBody = RequestUtil.getResponse(request, Collections.emptyMap(), successMessage, "", HttpStatus.OK);

        logger.info("Order approved with id: {}", id);

        return ResponseEntity.ok().body(responseBody);
    }

    @PutMapping("/{id}/decline")
    public ResponseEntity<Response> declineOrder(@PathVariable Long id, @RequestBody String declineReason, HttpServletRequest request) {
        orderService.declineOrder(id, declineReason);
        String successMessage = "Order declined successfully!";
        Response responseBody = RequestUtil.getResponse(request, Collections.emptyMap(), successMessage, "", HttpStatus.OK);

        logger.info("Order declined with id: {}, Reason: {}", id, declineReason);

        return ResponseEntity.ok().body(responseBody);
    }

    @PutMapping("/{id}/fulfill")
    public ResponseEntity<Response> updateOrderToFulfilled(@PathVariable Long id, HttpServletRequest request) {
        orderService.updateOrderToFulfilled(id);
        String successMessage = "Order fulfilled successfully!";
        Response responseBody = RequestUtil.getResponse(request, Collections.emptyMap(), successMessage, "", HttpStatus.OK);

        logger.info("Order fulfilled with id: {}", id);

        return ResponseEntity.ok().body(responseBody);
    }
}
