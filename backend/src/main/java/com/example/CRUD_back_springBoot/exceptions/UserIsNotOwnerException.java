package com.example.CRUD_back_springBoot.exceptions;

public class UserIsNotOwnerException extends RuntimeException {
    public UserIsNotOwnerException(String message) {
        super(message);
    }
}
