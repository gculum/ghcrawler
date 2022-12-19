package com.ghcrawler.demo.api.controller;

import com.ghcrawler.demo.api.dto.RepoDto;
import com.ghcrawler.demo.api.errorhandling.FormatNotAcceptableException;
import com.ghcrawler.demo.api.service.GhFetchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@Controller
@RequestMapping("api/v1/repo")
public class RepoController {

    Logger logger = LoggerFactory.getLogger(RepoController.class);

    @Autowired
    GhFetchService ghFetchService;

    @GetMapping(value = "/{username}", produces = {"application/json", "application/xml", "application/stream+json"})
    public @ResponseBody Flux<RepoDto> getRepos(
            @PathVariable String username,
            @RequestHeader("Accept") String accept) {

        if(accept.equals(MediaType.APPLICATION_XML_VALUE)) {
            logger.warn(String.format("%s request issued for user %s", MediaType.APPLICATION_XML_VALUE, username));
            throw new FormatNotAcceptableException(MediaType.APPLICATION_XML_VALUE);
        }

        logger.info(String.format("GET request issued for repository fetch for user %s", username));

        return ghFetchService.getRepoData(username);
    }


}
