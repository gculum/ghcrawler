package com.ghcrawler.demo.api.errorhandling;


public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super(String.format("Requested resource not found"));
    }
}