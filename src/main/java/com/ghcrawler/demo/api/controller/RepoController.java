package com.ghcrawler.demo.api.controller;

import com.ghcrawler.demo.api.dto.Branch;
import com.ghcrawler.demo.api.dto.Repo;
import com.ghcrawler.demo.api.errorhandling.FormatNotAcceptableException;
import com.ghcrawler.demo.api.service.GhFetchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("api/v1/repo")
public class RepoController {


    @Autowired
    GhFetchService ghFetchService;

    @GetMapping(value = "/{username}", produces = {"application/json", "application/xml", "application/stream+json"})
    public @ResponseBody Flux<Repo> getRepos(
            @PathVariable String username,
            @RequestHeader("Accept") String accept) {

        if(accept.equals(MediaType.APPLICATION_XML_VALUE)) {
            throw new FormatNotAcceptableException(MediaType.APPLICATION_XML_VALUE);
        }

        /*Repo repo1 = new Repo();
        repo1.setName("repo 1");
        repo1.getBranches().add(new Branch("name1", "sha1"));
        repo1.setBranches2(Flux.just(new Branch("name1", "sha1")));
        Repo repo2 = new Repo();
        repo2.getBranches().add(new Branch("name2", "sha2"));
        repo2.setName("repo 2");*/

        //return Flux.just(repo1, repo2);

        /*Flux<Repo> repo = ghFetchService.getRepoData(username);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
        return ghFetchService.getRepoData(username);
        /*return ghFetchService.getRepoData(username).map( u -> ResponseEntity.ok(u))
                .defaultIfEmpty(ResponseEntity.notFound().build());*/
    }


}
