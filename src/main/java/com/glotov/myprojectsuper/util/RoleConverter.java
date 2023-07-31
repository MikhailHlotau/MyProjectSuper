package com.glotov.myprojectsuper.util;

import com.glotov.myprojectsuper.model.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class RoleConverter implements AttributeConverter<Role, String> {

    @Override
    public String convertToDatabaseColumn(Role role) {
        return role != null ? role.getName() : null;
    }

    @Override
    public Role convertToEntityAttribute(String roleName) {
        return Role.fromName(roleName);
    }
}

