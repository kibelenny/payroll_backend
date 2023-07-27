package com.payroll_backend.controllers;


import com.payroll_backend.error.GlobalError;
import com.payroll_backend.models.Business;
import com.payroll_backend.services.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

/**
 * @version 1.0
 */
@RequiredArgsConstructor
@RequestMapping("v2/business")
@RestController
public class BusinessController {

    private final BusinessService businessService;

    @PostMapping("register")
    public ResponseEntity<?> save(@RequestBody Map<String, Object> map) {
        Business business = businessService.save(map);
        return business != null ?
                ResponseEntity.status(201).body(business) :
                ResponseEntity.status(415).body(new GlobalError((short) 415, "Not saved"));
    }


}
