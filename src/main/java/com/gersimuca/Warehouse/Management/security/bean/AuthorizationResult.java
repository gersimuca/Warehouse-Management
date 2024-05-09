package com.gersimuca.Warehouse.Management.security.bean;

import lombok.Data;

import java.util.List;

@Data
public class AuthorizationResult {

    private String message;
    private String username;
    private List<String> categoryIds;
    private List<String> userRoles;
}
