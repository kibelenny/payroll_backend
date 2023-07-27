package com.payroll_backend.error;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Will be used to customize the errors that arise from the endpoints
 * And their respective status code
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class GlobalError {
    private short status;
    private String message;
}
