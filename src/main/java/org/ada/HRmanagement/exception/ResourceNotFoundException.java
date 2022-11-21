package org.ada.HRmanagement.exception;

public class ResourceNotFoundException extends RuntimeException{

    public static final String MESSAGE = "El recurso consultado no existe";

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
