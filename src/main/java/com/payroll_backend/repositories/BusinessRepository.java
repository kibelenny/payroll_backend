package com.payroll_backend.repositories;

import com.payroll_backend.models.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BusinessRepository extends JpaRepository<Business, UUID> {

}
