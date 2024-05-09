package com.gersimuca.Warehouse.Management.security.bean;

import lombok.Data;

import java.util.List;

@Data
public class AuthenticationPrincipal {
    private String username;
    private String displayName;
    private List<String> roles;
}
