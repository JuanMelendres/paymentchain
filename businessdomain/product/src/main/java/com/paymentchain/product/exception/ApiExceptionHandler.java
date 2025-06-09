package com.paymentchain.product.exception;

import com.paymentchain.product.common.StandardizedApiExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
