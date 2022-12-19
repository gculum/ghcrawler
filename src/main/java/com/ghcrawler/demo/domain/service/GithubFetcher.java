package com.ghcrawler.demo.domain.service;

import com.ghcrawler.demo.domain.model.branch.Branch;
import com.ghcrawler.demo.domain.model.repo.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@Service
public class GithubFetcher implements IGithubApi {

    private String REPO_URL = "https://api.github.com/users/<USER-NAME>/repos";
    private String BRANCH_URL = "https://api.github.com/repos/<USER-NAME>/<REPO>/branches";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    WebClient webClient;

    @Override
    public Flux<Repository> getRepos(String username) {
        String url = REPO_URL.replace("<USER-NAME>", username);

        Flux<Repository> repositories = webClient
                .get()
                .uri(url)
                .header("Authorization", "5Eo89RQHlgLJrESfDSvRZKvvYY+WNCTt7nBCmd33w14")
                .retrieve()
                .bodyToFlux(Repository.class);
        return repositories;
    }


    @Override
    public List<Branch> getRepoBranches(String username, String repo) {
        String url = BRANCH_URL.replace("<USER-NAME>", username).
                replace("<REPO>", repo);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "5Eo89RQHlgLJrESfDSvRZKvvYY+WNCTt7nBCmd33w14 ");
        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<Branch[]> response = restTemplate.exchange(url, HttpMethod.GET, request, Branch[].class);

        return response != null && response.getStatusCode() == HttpStatus.OK ?
                List.of(response.getBody()) : null;
    }
}
