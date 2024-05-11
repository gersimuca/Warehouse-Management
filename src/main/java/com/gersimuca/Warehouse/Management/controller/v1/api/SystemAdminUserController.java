package com.gersimuca.Warehouse.Management.controller.v1.api;

import com.gersimuca.Warehouse.Management.domain.Response;
import com.gersimuca.Warehouse.Management.dto.request.UserRequest;
import com.gersimuca.Warehouse.Management.service.UserService;
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
@RequestMapping("/api/v1/system/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('SYSTEM_ADMIN')")
@Tag(name = "Admin Operation on User")
public class SystemAdminUserController {

    private static final Logger logger = LogManager.getLogger(SystemAdminUserController.class);

    private final UserService userService;

    @PutMapping("/{username}")
    public ResponseEntity<Response> updateUser(@PathVariable String username, @RequestBody UserRequest userRequest, HttpServletRequest request) {
        userService.updateUser(username, userRequest);
        String successMessage = "User updated successfully!";
        Response responseBody = RequestUtil.getResponse(request, Collections.emptyMap(), successMessage, "", HttpStatus.OK);

        logger.info("User {} updated", username);

        return ResponseEntity.ok().body(responseBody);
    }

    @GetMapping
    public ResponseEntity<Response> getAllUsers(HttpServletRequest request) {
        Map<?, ?> data = userService.getAllUsers();
        String successMessage = "Users retrieved successfully!";
        Response responseBody = RequestUtil.getResponse(request, data, successMessage, "", HttpStatus.OK);

        logger.info("All users retrieved");

        return ResponseEntity.ok().body(responseBody);
    }

    @PostMapping
    public ResponseEntity<Response> createUser(@RequestBody UserRequest userRequest, HttpServletRequest request) {
        userService.createUser(userRequest);
        String successMessage = "User created successfully!";

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(userRequest.getUsername())
                .toUri();

        Response responseBody = RequestUtil.getResponse(request, Collections.emptyMap(), successMessage, "", HttpStatus.OK);

        logger.info("User created: {}", userRequest.getUsername());

        return ResponseEntity.created(location).body(responseBody);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Response> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);

        logger.info("User {} deleted", username);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(username)
                .toUri();

        return ResponseEntity.noContent().location(location).build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<Response> getUserByUsername(@PathVariable String username, HttpServletRequest request) {
        Map<?, ?> data = userService.getUserByUsername(username);
        String successMessage = "User retrieved successfully!";
        Response responseBody = RequestUtil.getResponse(request, data, successMessage, "", HttpStatus.OK);

        logger.info("User {} retrieved", username);

        return ResponseEntity.ok().body(responseBody);
    }
}
