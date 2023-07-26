package com.payroll_backend.models;

import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @author Albert Ejuku
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@MappedSuperclass
public class Person {

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phone;


    private LocalDateTime dateCreated = LocalDateTime.now();
    private LocalDateTime dateModified;

}
