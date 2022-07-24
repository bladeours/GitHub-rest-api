package com.restapi.controller;

import com.restapi.exceptions.ApiBadRequestException;
import com.restapi.exceptions.ApiInternalServerErrorException;
import com.restapi.exceptions.ApiNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@RestController
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    @RequestMapping("/error")
    public void handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        System.out.println(status);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                throw new ApiNotFoundException("Unable to find");
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                throw new ApiInternalServerErrorException("Internal server error");
            } else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
                throw new ApiBadRequestException("Bad Request");
//                System.out.println("no ewidewtnie");
            }
        }
    }

}
