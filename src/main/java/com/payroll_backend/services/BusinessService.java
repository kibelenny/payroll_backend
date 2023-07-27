package com.payroll_backend.services;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.payroll_backend.error.UserExistsException;
import com.payroll_backend.models.AppUser;
import com.payroll_backend.models.Business;
import com.payroll_backend.models.ContactInformation;
import com.payroll_backend.models.UserRoles;
import com.payroll_backend.repositories.AppUserRepo;
import com.payroll_backend.repositories.BusinessRepository;
import com.payroll_backend.repositories.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @version 1.0
 */
@RequiredArgsConstructor
@Service
public class BusinessService {

    private final BusinessRepository businessRepository;
    private final ObjectMapper objectMapper;
    private  final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRoleRepository userRoleRepository;
    private final AppUserRepo appUserRepository;


    @Transactional
    public Business save(Map<String, Object> map) {
        Map<String, Object> userMap = objectMapper.convertValue(map.get("admin"), Map.class);
        Map<String, Object> businessMap = objectMapper.convertValue(map.get("business"), Map.class);
        Map<String, Object> contactMap = objectMapper.convertValue(map.get("contactInformation"), Map.class);

        UserRoles userRoles = userRoleRepository.findAllByNameAndDefaultValue("SUPER_ADMIN", true);

        if(userRoles == null) {
            UserRoles userRole = new UserRoles();
            userRole.setName("SUPER_ADMIN");
            userRole.setDefaultValue(true);
            userRole.setDescription("SUPER_ADMIN");
            userRole.setPermissions("ALL_PRIVILEGES");
            userRole.setDateCreated(LocalDateTime.now());
            userRoles = userRoleRepository.save(userRole);
        }

        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setName(String.valueOf(businessMap.get("businessName")));
        contactInformation.setEmail(String.valueOf(contactMap.get("email")));
        contactInformation.setPhone(String.valueOf(contactMap.get("phone")));
        contactInformation.setType("Business");
        contactInformation.setWebsite(String.valueOf(contactMap.get("website")));
        contactInformation.setAddress(String.valueOf(contactMap.get("address")));
        contactInformation.setCity(String.valueOf(contactMap.get("city")));
        contactInformation.setCountry(String.valueOf(contactMap.get("country")));

        Business business = new Business();
        business.setName(String.valueOf(businessMap.get("businessName")));
        business.setDescription(String.valueOf(businessMap.get("description")));
        business.setDateCreated(LocalDateTime.now());
        business.setContactInformation(contactInformation);

        business = businessRepository.save(business);

        AppUser appUser = new AppUser();
        appUser.setFirstName(String.valueOf(userMap.get("firstName")));
        appUser.setLastName(String.valueOf(userMap.get("lastName")));
        appUser.setUsername(String.valueOf(userMap.get("username")));
        appUser.setEmail(String.valueOf(userMap.get("email")));
        appUser.setPhone(String.valueOf(userMap.get("phone")));
        appUser.setPassword(passwordEncoder.encode(String.valueOf(userMap.get("password"))));
        appUser.setBusiness(business);
        appUser.setRole(userRoles);

        AppUser appUser1 = appUserRepository.findByEmail(appUser.getEmail());


        if(appUser1 != null) {
            try {
                throw new UserExistsException("User Exists");
            } catch (UserExistsException e) {
                throw new RuntimeException(e);
            }
        } else {
            appUser = appUserRepository.save(appUser);
            business.setCreatedBy(appUser.getId());
            businessRepository.save(business);
        }

        return business;
    }


}
