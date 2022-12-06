package org.ada.HRmanagement.exceptions;

public class InvalidDataException extends RuntimeException{

    public static final String MESSAGE = "El dato ingresado no es válido";

    public InvalidDataException() {
        super(MESSAGE);
    }

    public InvalidDataException(String message) {
        super(message);
    }


}
