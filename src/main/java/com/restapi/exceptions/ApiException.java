package com.restapi.exceptions;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException {
    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;
    private final int status;

    public ApiException(String message, HttpStatus httpStatus, ZonedDateTime timestamp, int status) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
        this.status = status;
    }


    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }
}
