package com.fipe.domain.enums;

import lombok.Getter;

public enum Role {
    ADMIN("ADMIN", "System Administrator"),
    USER("USER", "Standard User"),
    MANAGER("MANAGER", "Manager"),
    VIEWER("VIEWER", "Viewer");
    
    @Getter
    private final String name;

    @Getter
    private final String description;
    
    Role(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    public static Role fromString(String role) {
        for (Role r : Role.values()) {
            if (r.name.equalsIgnoreCase(role)) {
                return r;
            }
        }
        throw new IllegalArgumentException("Invalid role: " + role);
    }
}
