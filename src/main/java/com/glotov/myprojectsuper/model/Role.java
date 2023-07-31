package com.glotov.myprojectsuper.model;

import lombok.Getter;

@Getter
public enum Role {
    USER("USER"),
    ADMIN("ADMIN");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    public static Role fromName(String roleName) {
        for (Role role : Role.values()) {
            if (role.getName().equals(roleName)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown role name: " + roleName);
    }
}


