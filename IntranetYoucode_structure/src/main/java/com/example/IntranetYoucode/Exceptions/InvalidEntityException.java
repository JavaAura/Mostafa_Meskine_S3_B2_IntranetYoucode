package com.example.IntranetYoucode.Exceptions;

public class InvalidEntityException extends RuntimeException {
    public InvalidEntityException(String entityName, String message) {
        super("Invalid " + entityName + ": " + message);
    }
}
