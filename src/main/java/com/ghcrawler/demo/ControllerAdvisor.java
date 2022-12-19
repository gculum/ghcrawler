package com.ghcrawler.demo;

import com.ghcrawler.demo.api.errorhandling.FormatNotAcceptableException;
import com.ghcrawler.demo.api.errorhandling.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {


    Logger logger = LoggerFactory.getLogger(ControllerAdvisor.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {


        logger.warn(String.format("ResourceNotFoundException [%s] raised", ex.getMessage()));

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", HttpStatus.NOT_FOUND);
        body.put("message", ex.getMessage());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(body, headers, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(FormatNotAcceptableException.class)
    public ResponseEntity<Object> handleFormatNotAcceptableException(
            FormatNotAcceptableException ex, WebRequest request) {

        logger.warn(String.format("ResourceNotFoundException [%s] raised", ex.getMessage()));

        //TODO consider implement proper model class instead of HashMap
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", HttpStatus.NOT_ACCEPTABLE);
        body.put("message", ex.getMessage());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(body, headers, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(
            RuntimeException ex, WebRequest request) {

        logger.warn(String.format("RuntimeException [%s] raised", ex.getMessage()));

        //TODO consider implement proper model class instead of HashMap
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", HttpStatus.NOT_ACCEPTABLE);
        body.put("message", ex.getMessage());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(body, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}