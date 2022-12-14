package com.ghcrawler.demo;

import com.ghcrawler.demo.api.errorhandling.ResourceNotFoundException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

public class MyApiRestTemplateErrorHandler extends DefaultResponseErrorHandler {
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if(response.getStatusCode().is4xxClientError()) {
            throw new ResourceNotFoundException();
        }
    }
}
