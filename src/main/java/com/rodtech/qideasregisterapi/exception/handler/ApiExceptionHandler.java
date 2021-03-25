package com.rodtech.qideasregisterapi.exception.handler;

import com.rodtech.qideasregisterapi.exception.RegisteredEmailException;
import com.rodtech.qideasregisterapi.exception.AccountNotFoundException;
import com.rodtech.qideasregisterapi.exception.RegisteredUsernameException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String VALIDATION_ERROR = "Validation Error";

    /**
     * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
     *
     * @param ex      the MethodArgumentNotValidException that is thrown when @Valid validation fails
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, VALIDATION_ERROR, errors);
        return handleExceptionInternal(
                ex, apiError, headers, apiError.getStatus(), request);
    }


    @ExceptionHandler(RegisteredEmailException.class)
    protected ResponseEntity<Object> handleRegisteredEmailException(
            RegisteredEmailException ex,
            WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), Collections.emptyList());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }


    @ExceptionHandler(RegisteredUsernameException.class)
    protected ResponseEntity<Object> handleRegisteredUsernameException(
            RegisteredEmailException ex,
            WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), Collections.emptyList());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }


    @ExceptionHandler(AccountNotFoundException.class)
    protected ResponseEntity<Object> handleAccountNotFoundException(
            AccountNotFoundException ex,
            WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), Collections.emptyList());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

}
