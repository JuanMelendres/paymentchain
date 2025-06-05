package com.paymentchain.customer.exception;

import com.paymentchain.customer.common.StandardizedApiExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<?> handleUnknowHostException (Exception ex){
        StandardizedApiExceptionResponse standardizedApiExceptionResponse =
                new StandardizedApiExceptionResponse("TECH","I/O error","1024",ex.getMessage());

        return new ResponseEntity<>(standardizedApiExceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { BusinessRuleException.class })
    public ResponseEntity<?> handleBusinessRuleException(BusinessRuleException ex){
        StandardizedApiExceptionResponse standardizedApiExceptionResponse =
                new StandardizedApiExceptionResponse("BUSINESS","Validation error",ex.getCode(),ex.getMessage());

        return new ResponseEntity<>(standardizedApiExceptionResponse, ex.getHttpStatus());
    }
}
