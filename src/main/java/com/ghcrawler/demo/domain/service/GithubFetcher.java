package com.ghcrawler.demo.domain.service;

import com.ghcrawler.demo.domain.model.branch.Branch;
import com.ghcrawler.demo.domain.model.repo.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GithubFetcher implements IGithubApi {

    private String REPO_URL = "https://api.github.com/users/<USER-NAME>/repos";
    private String BRANCH_URL = "https://api.github.com/repos/<USER-NAME>/<REPO>/branches";

    @Autowired
    RestTemplate restTemplate;

    @Override
    public List<Repository> getRepos(String username) {
        String url = REPO_URL.replace("<USER-NAME>", username);
        ResponseEntity<Repository[]> response = restTemplate.getForEntity(url, Repository[].class);
        return response != null && response.getStatusCode() == HttpStatus.OK ?
                List.of(response.getBody()) : null;
    }

    @Override
    public List<Branch> getRepoBranches(String username, String repo) {
        String url = BRANCH_URL.replace("<USER-NAME>", username).
                replace("<REPO>", repo);
        ResponseEntity<Branch[]> response = restTemplate.getForEntity(url, Branch[].class);
        return response != null && response.getStatusCode() == HttpStatus.OK ?
                List.of(response.getBody()) : null;
    }
}
