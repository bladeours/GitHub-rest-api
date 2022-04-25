package com.restapi.exceptions;

public class ApiUnauthorizedException extends RuntimeException{
    public ApiUnauthorizedException(String message) {
        super(message);
    }
}
