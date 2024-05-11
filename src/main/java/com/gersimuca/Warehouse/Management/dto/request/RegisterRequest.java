package com.gersimuca.Warehouse.Management.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gersimuca.Warehouse.Management.enumeration.Role;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotEmpty(message = "Username cannot be empty or null")
    private String username;
    @NotEmpty(message = "Password cannot be empty or null")
    private String password;

    private Role role;
}
