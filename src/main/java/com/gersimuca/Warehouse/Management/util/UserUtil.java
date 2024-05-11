package com.gersimuca.Warehouse.Management.util;

import com.gersimuca.Warehouse.Management.enumeration.Role;
import com.gersimuca.Warehouse.Management.enumeration.TokenType;
import com.gersimuca.Warehouse.Management.model.Token;
import com.gersimuca.Warehouse.Management.model.User;

import java.util.List;

public class UserUtil {
    public static User saveUser(String username, String password, Role role) {
        return User.builder()
                .username(username)
                .password(password)
                .role(role)
                .build();
    }
}
