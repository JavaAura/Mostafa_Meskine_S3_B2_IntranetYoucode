package com.example.IntranetYoucode.Exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entityName, Long id) {
        super(entityName + " not found with ID: " + id);
    }
}

