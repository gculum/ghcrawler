package com.ghcrawler.demo.api.controller;

import com.ghcrawler.demo.api.dto.Repo;
import com.ghcrawler.demo.api.errorhandling.FormatNotAcceptableException;
import com.ghcrawler.demo.api.service.GhFetchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("api/v1/repo")
public class RepoController {


    @Autowired
    GhFetchService ghFetchService;

    @GetMapping(value = "/{username}", produces = {"application/json", "application/xml"})
    public @ResponseBody ResponseEntity<List<Repo>> getRepos(
            @PathVariable String username,
            @RequestHeader("Accept") String accept) {

        if(accept.equals(MediaType.APPLICATION_XML_VALUE)) {
            throw new FormatNotAcceptableException(MediaType.APPLICATION_XML_VALUE);
        }

        return new ResponseEntity<>(ghFetchService.getRepoData(username), HttpStatus.OK);
    }


}
