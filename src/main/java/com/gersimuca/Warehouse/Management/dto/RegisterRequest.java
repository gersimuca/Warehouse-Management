package com.gersimuca.Warehouse.Management.dto;

import com.gersimuca.Warehouse.Management.enumeration.Role;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
