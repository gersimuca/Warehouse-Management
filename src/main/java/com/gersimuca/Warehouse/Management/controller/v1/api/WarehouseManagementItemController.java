package com.gersimuca.Warehouse.Management.controller.v1.api;

import com.gersimuca.Warehouse.Management.domain.Response;
import com.gersimuca.Warehouse.Management.dto.request.ItemRequest;
import com.gersimuca.Warehouse.Management.service.ItemService;
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
@RequestMapping("/api/v1/management/items")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('WAREHOUSE_MANAGER')")
@Tag(name = "Manager Operation on Items")
public class WarehouseManagementItemController {

    private static final Logger logger = LogManager.getLogger(WarehouseManagementItemController.class);

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<Response> getAllItems(HttpServletRequest request) {
        Map<?, ?> data = itemService.getAllItems();
        String successMessage = "Items retrieved successfully!";
        Response responseBody = RequestUtil.getResponse(request, data, successMessage, "", HttpStatus.OK);

        logger.info("All items retrieved");

        return ResponseEntity.ok().body(responseBody);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getItemById(@PathVariable Long id, HttpServletRequest request) {
        Map<?, ?> data = itemService.getItemById(id);
        String successMessage = "Item retrieved successfully!";
        Response responseBody = RequestUtil.getResponse(request, data, successMessage, "", HttpStatus.OK);

        logger.info("Item retrieved by id: {}", id);

        return ResponseEntity.ok().body(responseBody);
    }

    @PostMapping
    public ResponseEntity<Response> createItem(@RequestBody ItemRequest itemRequest, HttpServletRequest request) {
        itemService.createItem(itemRequest);
        String successMessage = "Item created successfully!";
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .build()
                .toUri();

        Response responseBody = RequestUtil.getResponse(request, Collections.emptyMap(), successMessage, "", HttpStatus.CREATED);

        logger.info("Item created: {}", itemRequest);

        return ResponseEntity.created(location).body(responseBody);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateItem(@PathVariable Long id, @RequestBody ItemRequest updatedItem, HttpServletRequest request) {
        itemService.updateItem(id, updatedItem);
        String successMessage = "Item updated successfully!";
        Response responseBody = RequestUtil.getResponse(request, Collections.emptyMap(), successMessage, "", HttpStatus.OK);

        logger.info("Item updated with id {}: {}", id, updatedItem);

        return ResponseEntity.ok().body(responseBody);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteItem(@PathVariable Long id, HttpServletRequest request) {
        itemService.deleteItem(id);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        logger.info("Item deleted with id: {}", id);

        return ResponseEntity.noContent().location(location).build();
    }
}
