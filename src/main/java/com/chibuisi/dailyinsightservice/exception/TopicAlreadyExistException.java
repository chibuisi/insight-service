package com.chibuisi.dailyinsightservice.exception;

public class TopicAlreadyExistException extends RuntimeException{
    public TopicAlreadyExistException(String message){
        super(message);
    }
}
