package com.gersimuca.Warehouse.Management.controller.v1.api;

import com.gersimuca.Warehouse.Management.domain.Response;
import com.gersimuca.Warehouse.Management.dto.request.AuthenticationRequest;
import com.gersimuca.Warehouse.Management.dto.request.RegisterRequest;
import com.gersimuca.Warehouse.Management.service.AuthenticationService;
import com.gersimuca.Warehouse.Management.util.request.RequestUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {

    private static final Logger logger = LogManager.getLogger(AuthenticationController.class);

    private final AuthenticationService authenticationService;

    @Operation(summary = "Register a new user",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Account created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class)))
            })
    @PostMapping("/register")
    public ResponseEntity<Response> register(@Valid @RequestBody RegisterRequest register, HttpServletRequest request){
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{username}")
                .buildAndExpand(register.getUsername())
                .toUri();

        String successMessage = "Account created successfully!";
        HttpStatus status = HttpStatus.CREATED;

        Map<?, ?> data = authenticationService.register(register);
        Response responseBody = RequestUtil.getResponse(request, data, successMessage, "", status);

        logger.info("New account registered: {}", register.getUsername());

        return ResponseEntity.created(location).body(responseBody);
    }

    @Operation(summary = "Login with username and password",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Account login successful",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class)))
            })
    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody AuthenticationRequest auth, HttpServletRequest request){
        Map<?, ?> data = authenticationService.authenticate(auth);

        String successMessage = "Account login successfully!";

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{access_token}")
                .buildAndExpand(data.get("access_token"))
                .toUri();

        Response responseBody = RequestUtil.getResponse(request, data, successMessage, "", HttpStatus.CREATED);

        logger.info("User logged in: {}", auth.getUsername());

        return ResponseEntity.created(location).body(responseBody);
    }
}
