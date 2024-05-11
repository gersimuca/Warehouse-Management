package com.gersimuca.Warehouse.Management.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationRequest {

    private String username;
    String password;
}
