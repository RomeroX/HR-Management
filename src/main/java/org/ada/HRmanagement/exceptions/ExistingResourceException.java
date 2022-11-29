package org.ada.HRmanagement.exceptions;

public class ExistingResourceException extends RuntimeException{

    public final static String MESSAGE = "El recurso que se está intentando crear ya existe";

    public ExistingResourceException(){
        super(MESSAGE);
    }

    public ExistingResourceException(String message) {
        super(message);
    }
}
