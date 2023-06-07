package com.chibuisi.dailyinsightservice.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class AuthenticationFailedException extends BadCredentialsException {
    public AuthenticationFailedException(String msg, Throwable throwable) {
        super(msg);
    }
}
