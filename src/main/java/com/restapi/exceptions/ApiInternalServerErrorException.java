package com.restapi.exceptions;

public class ApiInternalServerErrorException extends RuntimeException{
    public ApiInternalServerErrorException(String message) {
        super(message);
    }
}
