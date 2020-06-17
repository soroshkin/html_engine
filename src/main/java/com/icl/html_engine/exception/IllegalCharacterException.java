package com.icl.html_engine.exception;

public class IllegalCharacterException extends NotValidInputException {
    public IllegalCharacterException() {
        super();
    }

    public IllegalCharacterException(String message) {
        super(message);
    }

    public IllegalCharacterException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalCharacterException(Throwable cause) {
        super(cause);
    }
}
