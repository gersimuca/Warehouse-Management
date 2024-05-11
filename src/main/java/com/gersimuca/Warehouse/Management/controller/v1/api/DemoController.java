package com.gersimuca.Warehouse.Management.controller.v1.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
@Tag(name = "Secure Endpoint")
public class DemoController {

    private static final Logger logger = LogManager.getLogger(AuthenticationController.class);

    @GetMapping
    public ResponseEntity<String> sayHello(){

        logger.info("JWT working properly");
        return ResponseEntity.ok("HELLO you made it :)");
    }
}
