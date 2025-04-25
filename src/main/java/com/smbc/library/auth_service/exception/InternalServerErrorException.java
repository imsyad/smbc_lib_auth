package com.smbc.library.auth_service.exception;

public class InternalServerErrorException extends RuntimeException {

    public InternalServerErrorException() {
        super("Internal server error occurred.");
    }

    public InternalServerErrorException(String message) {
        super(message);
    }

    public InternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
