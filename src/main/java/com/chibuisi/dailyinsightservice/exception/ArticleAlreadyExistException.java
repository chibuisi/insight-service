package com.chibuisi.dailyinsightservice.exception;

public class ArticleAlreadyExistException extends RuntimeException {
    public ArticleAlreadyExistException(String message) {
        super(message);
    }
}
