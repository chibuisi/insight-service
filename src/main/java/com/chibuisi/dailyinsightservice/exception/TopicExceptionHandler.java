package com.chibuisi.dailyinsightservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TopicExceptionHandler {
    @ExceptionHandler(TopicAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String topicAlreadyExistsMessage(TopicAlreadyExistException exception){
        return exception.getMessage();
    }

    @ExceptionHandler(TopicNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String userNotFoundMessage(TopicNotFoundException exception){
        return exception.getMessage();
    }
}
