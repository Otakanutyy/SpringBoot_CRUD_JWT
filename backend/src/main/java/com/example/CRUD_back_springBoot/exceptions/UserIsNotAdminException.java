package com.example.CRUD_back_springBoot.exceptions;

public class UserIsNotAdminException extends RuntimeException {
    public UserIsNotAdminException(String message) {
        super(message);
    }
}
