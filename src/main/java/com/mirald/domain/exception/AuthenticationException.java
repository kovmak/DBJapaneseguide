package com.mirald.domain.exception;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException() {
        super("Invalid login or password.");
    }
}
