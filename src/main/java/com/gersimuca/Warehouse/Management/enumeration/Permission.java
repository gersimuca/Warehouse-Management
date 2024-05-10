package com.gersimuca.Warehouse.Management.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    WAREHOUSE_MANAGER_READ("management:read"),
    WAREHOUSE_MANAGER_UPDATE("management:update"),
    WAREHOUSE_MANAGER_CREATE("management:create"),
    WAREHOUSE_MANAGER_DELETE("management:delete"),
    SYSTEM_ADMIN_READ("admin:read"),
    SYSTEM_ADMIN_UPDATE("admin:update"),
    SYSTEM_ADMIN_CREATE("admin:create"),
    SYSTEM_ADMIN_DELETE("admin:delete");


    private final String permission;
}
