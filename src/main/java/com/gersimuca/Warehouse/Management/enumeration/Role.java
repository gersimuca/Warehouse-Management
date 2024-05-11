package com.gersimuca.Warehouse.Management.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum Role {
    CLIENT(Collections.emptySet()),
    WAREHOUSE_MANAGER(
            Set.of(
                    Permission.WAREHOUSE_MANAGER_READ,
                    Permission.WAREHOUSE_MANAGER_UPDATE,
                    Permission.WAREHOUSE_MANAGER_DELETE,
                    Permission.WAREHOUSE_MANAGER_CREATE
            )
    ),
    SYSTEM_ADMIN(
            Set.of(
                    Permission.SYSTEM_ADMIN_READ,
                    Permission.SYSTEM_ADMIN_CREATE,
                    Permission.SYSTEM_ADMIN_UPDATE,
                    Permission.SYSTEM_ADMIN_DELETE
            )
    );


    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
