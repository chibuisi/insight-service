package com.chibuisi.dailyinsightservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthenticationFailedExceptionHandler {
    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<?> handleAuthenticationFailures(AuthenticationFailedException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}
