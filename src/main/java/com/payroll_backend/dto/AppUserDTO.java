package com.payroll_backend.dto;

import java.util.List;
import java.util.UUID;

public record AppUserDTO(
        UUID id,
        String firstName,
        String lastName,
        String username,
        String email,
        String phone,
        String title,
        String description,
        List<String> permissions
) {
}
