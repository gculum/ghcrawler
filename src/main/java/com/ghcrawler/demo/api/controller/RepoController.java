package com.ghcrawler.demo.api.controller;

import com.ghcrawler.demo.api.dto.Repo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("repos")
public class RepoController {

    @GetMapping(value = "/{username}", produces = "application/json")
    public @ResponseBody List<Repo> getRepos(@PathVariable String username) {

        //TODO remove this
        List<Repo> repos = new ArrayList<>();
        Repo repo = new Repo();
        repo.setName("Nikola");
        repos.add(repo);

        return repos;
    }
}
