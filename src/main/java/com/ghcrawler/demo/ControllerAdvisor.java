package com.ghcrawler.demo;

import com.ghcrawler.demo.api.errorhandling.FormatNotAcceptableException;
import com.ghcrawler.demo.api.errorhandling.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", HttpStatus.NOT_FOUND);
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(FormatNotAcceptableException.class)
    public ResponseEntity<Object> handleFormatNotAcceptableException(
            FormatNotAcceptableException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", HttpStatus.NOT_ACCEPTABLE);
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_ACCEPTABLE);
    }

}