package com.gersimuca.Warehouse.Management.controller;

import com.gersimuca.Warehouse.Management.domain.Response;
import com.gersimuca.Warehouse.Management.dto.ItemRequest;
import com.gersimuca.Warehouse.Management.service.OrderService;
import com.gersimuca.Warehouse.Management.util.request.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import static org.apache.logging.log4j.core.config.plugins.Plugin.EMPTY;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ClientController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<Response> order(@RequestBody List<ItemRequest> itemRequests, HttpServletRequest request){
        String successMessage = "Order created successfully!";
        HttpStatus status = HttpStatus.OK;
        URI location = getUri();

        String authHeader = request.getHeader("Authorization");
        String jwt = authHeader.substring(7);

        orderService.createOrder(itemRequests, jwt);

        Response responseBody = RequestUtil.getResponse(request, Collections.emptyMap(), successMessage, EMPTY, status);
        return ResponseEntity.created(location).body(responseBody);
    }

    private URI getUri() {
        return URI.create("");
    }
}
