package com.gersimuca.Warehouse.Management.security.bean;

import com.gersimuca.Warehouse.Management.security.enumeration.AuthenticationCredentialsType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationCredentials {
    private AuthenticationCredentialsType credentialsType;
    private String credentials;
}

