package com.ghcrawler.demo.api.errorhandling;


public class FormatNotAcceptableException extends RuntimeException {

    public FormatNotAcceptableException(String format) {
        super(String.format("Format %s not acceptable", format));
    }
}