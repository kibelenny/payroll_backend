package com.payroll_backend.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record UserRolesDTO(
        UUID id,
        String name,
        String description,
        boolean defaultValue,
        LocalDateTime dateCreated,
        List<String> permissions
) {
}
