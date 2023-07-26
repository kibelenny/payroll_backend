package com.payroll_backend.repositories;

import com.payroll_backend.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppUserRepo extends JpaRepository<AppUser, UUID> {
    AppUser findByEmail(String email);

}
