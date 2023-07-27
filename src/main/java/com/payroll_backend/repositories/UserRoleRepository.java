package com.payroll_backend.repositories;

import com.payroll_backend.models.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoles, UUID> {
    List<UserRoles> findAllByDefaultValue(boolean b);
    List<UserRoles> findAllByDefaultValueOrBusiness_Id(boolean b, UUID id);
    UserRoles findAllByNameAndDefaultValue(String name, boolean isDefault);
}
