package org.ada.HRmanagement.exception;

public class ExistingResourceException extends RuntimeException{

    public final static String MESSAGE = "El recurso que se está intentando crear ya existe";

    public ExistingResourceException(){

    }

    public ExistingResourceException(String message) {
        super(message);
    }
}
