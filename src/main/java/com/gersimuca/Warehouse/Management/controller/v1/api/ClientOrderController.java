package com.gersimuca.Warehouse.Management.controller.v1.api;

import com.gersimuca.Warehouse.Management.domain.Response;
import com.gersimuca.Warehouse.Management.dto.request.ItemRequest;
import com.gersimuca.Warehouse.Management.dto.request.OrderRequest;
import com.gersimuca.Warehouse.Management.enumeration.Status;
import com.gersimuca.Warehouse.Management.service.OrderService;
import com.gersimuca.Warehouse.Management.util.request.RequestUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.apache.logging.log4j.core.config.plugins.Plugin.EMPTY;

@RestController
@RequestMapping("/api/v1/client/orders")
@RequiredArgsConstructor
@Log4j2
@Tag(name = "Client Operation on Order")
public class ClientOrderController {

    private static final Logger logger = LogManager.getLogger(ClientOrderController.class);

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Response> createOrder(@RequestBody List<ItemRequest> itemRequests, HttpServletRequest request) {
        String successMessage = "Order created successfully!";
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .build()
                .toUri();

        String authHeader = request.getHeader("Authorization");
        String jwt = authHeader.substring(7);

        logger.info("Creating order...");
        orderService.createOrder(itemRequests, jwt);
        logger.info("Order created successfully");

        Response responseBody = RequestUtil.getResponse(request, Collections.emptyMap(), successMessage, EMPTY, HttpStatus.CREATED);
        return ResponseEntity.created(location).body(responseBody);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateOrder(@PathVariable Long id, @RequestBody OrderRequest orderRequest, HttpServletRequest request){
        String successMessage = "Order updated successfully!";

        logger.info("Updating order...");
        orderService.updateOrder(id, orderRequest);
        logger.info("Order updated successfully");

        Response responseBody = RequestUtil.getResponse(request, Collections.emptyMap(), successMessage, EMPTY, HttpStatus.OK);
        return ResponseEntity.ok().body(responseBody);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Response> cancelOrder(@PathVariable Long id, @RequestBody OrderRequest orderRequest, HttpServletRequest request){
        String successMessage = "Order cancel successfully!";

        logger.info("Cancelling order...");
        orderService.cancelOrder(id, orderRequest);
        logger.info("Order cancelled successfully");

        Response responseBody = RequestUtil.getResponse(request, Collections.emptyMap(), successMessage, EMPTY, HttpStatus.OK);
        return ResponseEntity.ok().body(responseBody);
    }

    @PutMapping("/{id}/submit")
    public ResponseEntity<Response> submitOrder(@PathVariable Long id, @RequestBody OrderRequest orderRequest, HttpServletRequest request){
        String successMessage = "Order submitted successfully!";

        logger.info("Submitting order...");
        orderService.submitOrder(id, orderRequest);
        logger.info("Order submitted successfully");

        Response responseBody = RequestUtil.getResponse(request, Collections.emptyMap(), successMessage, EMPTY, HttpStatus.OK);
        return ResponseEntity.ok().body(responseBody);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Response> getOrdersById(@PathVariable String username, HttpServletRequest request) {
        Map<?, ?> data = orderService.getOrdersByUsername(username);
        String successMessage = "Orders retrieved successfully!";
        Response responseBody = RequestUtil.getResponse(request, data, successMessage, "", HttpStatus.OK);

        logger.info("Orders retrieved by user: {}", data.size());

        return ResponseEntity.ok().body(responseBody);
    }

    @GetMapping("/{username}/status")
    public ResponseEntity<Response> getOrdersByUsernameAndStatus(@PathVariable String username, @RequestParam("status") Status status, HttpServletRequest request) {
        Map<?, ?> data = orderService.getOrdersByUserByStatus(username, status);
        String successMessage = "Orders retrieved successfully!";
        Response responseBody = RequestUtil.getResponse(request, data, successMessage, "", HttpStatus.OK);

        logger.info("Orders retrieved by user: {} by status", data.size());

        return ResponseEntity.ok().body(responseBody);
    }


}
