package com.payroll_backend.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payroll_backend.dto.UserRolesDTO;
import com.payroll_backend.enums.PagePermission;
import com.payroll_backend.models.AppUser;
import com.payroll_backend.models.Business;
import com.payroll_backend.models.UserRoles;
import com.payroll_backend.repositories.BusinessRepository;
import com.payroll_backend.repositories.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final BusinessRepository businessRepository;

    public UserRolesDTO saveRole(Map<String, Object> map) {
        UserRoles role = new UserRoles();
        role.setName(String.valueOf(map.get("name")));
        role.setDescription(String.valueOf(map.get("name")));
        String[] permissions = objectMapper.convertValue(map.get("permissions"), String[].class);
        Business business = businessRepository.findById(UUID.fromString(String.valueOf(map.get("businessId")))).orElse(null);

        if (business == null) {
            throw new IllegalStateException("No business found");
        }
        role.setBusiness(business);
        role.setCreatedBy(new AppUser(UUID.fromString(String.valueOf(map.get("createdBy")))));
        role.setDefaultValue(false);
        role.setDateCreated(LocalDateTime.now());

        StringBuilder permissionList = new StringBuilder();
        for (int i = 0; i < permissions.length; i++) {
            permissionList.append(PagePermission.valueOf(permissions[i]));
            if(i != permissions.length - 1) {
                permissionList.append(",");
            }
        }

        role.setPermissions(permissionList.toString());
        UserRolesDTO userRolesDTO = userRoleRepository.save(role).toDTO();
        return userRolesDTO;
    }

    public List<UserRoles> findAllDefaultRoles() {
        return userRoleRepository.findAllByDefaultValue(true);
    }


    public List<UserRolesDTO> findAllDefaultInBusiness(UUID id) {
        return  userRoleRepository.findAllByDefaultValueOrBusiness_Id(true, id)
                .stream()
                .map(role -> new UserRolesDTO(
                                role.getId(),
                                role.getName(),
                                role.getDescription(),
                                role.getBusiness().getId(),
                                role.isDefaultValue(),
                                role.getDateCreated(),
                                Arrays.asList(role.getPermissions().split(","))
                        )
                ).toList();
    }

}
