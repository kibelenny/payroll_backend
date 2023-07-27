package com.payroll_backend.error;

public class UserExistsException extends Throwable{

    public UserExistsException(String message) {
        super(message);
    }
}
