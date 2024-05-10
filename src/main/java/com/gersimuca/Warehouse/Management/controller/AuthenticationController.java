package com.gersimuca.Warehouse.Management.controller;

import com.gersimuca.Warehouse.Management.domain.Response;
import com.gersimuca.Warehouse.Management.dto.AuthenticationRequest;
import com.gersimuca.Warehouse.Management.dto.RegisterRequest;
import com.gersimuca.Warehouse.Management.service.AuthenticationService;
import com.gersimuca.Warehouse.Management.util.request.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Map;

import static org.apache.logging.log4j.core.config.plugins.Plugin.EMPTY;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@Valid @RequestBody RegisterRequest register, HttpServletRequest request){
        URI location = getUri();
        String successMessage = "Account created successfully!";
        HttpStatus status = HttpStatus.CREATED;

        Map<?, ?> data = authenticationService.register(register);
        Response responseBody = RequestUtil.getResponse(request, data, successMessage, EMPTY, status);


        return ResponseEntity.created(location).body(responseBody);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody AuthenticationRequest auth, HttpServletRequest request){
        String successMessage = "Account login successfully!";
        HttpStatus status = HttpStatus.OK;

        Map<?, ?> data = authenticationService.authenticate(auth);
        Response responseBody = RequestUtil.getResponse(request, data, successMessage, EMPTY, status);
        return ResponseEntity.ok(responseBody);
    }

    private URI getUri() {
        return URI.create("");
    }
}
