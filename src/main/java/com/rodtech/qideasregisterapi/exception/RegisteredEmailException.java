package com.rodtech.qideasregisterapi.exception;

public class RegisteredEmailException extends RuntimeException {
    public RegisteredEmailException(String message) {
        super(message);
    }
}
