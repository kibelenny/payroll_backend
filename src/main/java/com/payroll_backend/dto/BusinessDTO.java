package com.payroll_backend.dto;

import java.util.UUID;

public record BusinessDTO(
        UUID businessId,
        String name,
        String description
) {
}
