package com.restapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    //404 - not found
    @ExceptionHandler(ApiNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(ApiNotFoundException e){
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(e.getMessage(), notFound, ZonedDateTime.now(ZoneId.of("Z")),
                HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(apiException, notFound);
    }

    //500 - internal server error
    @ExceptionHandler(ApiInternalServerErrorException.class)
    public ResponseEntity<Object> handleInternalServerErrorException(ApiInternalServerErrorException e){
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiException apiException = new ApiException(e.getMessage(), internalServerError, ZonedDateTime.now(ZoneId.of("Z")),
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(apiException, internalServerError);
    }

    //400 - bad request
    @ExceptionHandler(ApiBadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(ApiBadRequestException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(e.getMessage(), badRequest, ZonedDateTime.now(ZoneId.of("Z")),
                HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(apiException, badRequest);
    }


    //405 - Method not allowed
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> ahandle(HttpRequestMethodNotSupportedException e){
        HttpStatus methodNotAllowed = HttpStatus.METHOD_NOT_ALLOWED;
        ApiException apiException = new ApiException(e.getMessage(), methodNotAllowed, ZonedDateTime.now(ZoneId.of("Z")),
                HttpStatus.METHOD_NOT_ALLOWED.value());
        return new ResponseEntity<>(apiException, methodNotAllowed);
    }

    //401 - Unauthorized
    @ExceptionHandler(ApiUnauthorizedException.class)
    public ResponseEntity<Object> ahandle(ApiUnauthorizedException e){
        HttpStatus Unauthorized = HttpStatus.UNAUTHORIZED;
        ApiException apiException = new ApiException(e.getMessage(), Unauthorized, ZonedDateTime.now(ZoneId.of("Z")),
                HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(apiException, Unauthorized);
    }
}
