package com.chibuisi.dailyinsightservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ArticleExceptionHandler {
    @ExceptionHandler(ArticleAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String articleAlreadyExistsMessage(ArticleAlreadyExistException exception){
        return exception.getMessage();
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String userNotFoundMessage(ArticleNotFoundException exception){
        return exception.getMessage();
    }

}
