package com.icl.html_engine.exception;

public class EmptyExpressionException extends NotValidInputException {
    public EmptyExpressionException() {
        super();
    }

    public EmptyExpressionException(String message) {
        super(message);
    }

    public EmptyExpressionException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyExpressionException(Throwable cause) {
        super(cause);
    }
}
