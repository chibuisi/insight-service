package com.chibuisi.dailyinsightservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String userAlreadyExistsMessage(UserAlreadyExistException exception){
        return exception.getMessage();
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String userNotFoundMessage(UserNotFoundException exception){
        return exception.getMessage();
    }

//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<?> handleGenericFailures(RuntimeException exception){
//        System.out.println("Exception occurred at this point: "+exception.getMessage());
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error occurred!");
//    }
}
