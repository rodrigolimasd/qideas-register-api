package com.rodtech.qideasregisterapi.exception;

public class RegisteredUsernameException extends RuntimeException {
    public RegisteredUsernameException(String message) {
        super(message);
    }
}
