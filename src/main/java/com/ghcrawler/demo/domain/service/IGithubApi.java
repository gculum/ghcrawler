package com.ghcrawler.demo.domain.service;

import com.ghcrawler.demo.domain.model.branch.Branch;
import com.ghcrawler.demo.domain.model.repo.Repository;

import java.util.List;

public interface IGithubApi {
    List<Repository> getRepos(String username);

    List<Branch> getRepoBranches(String username, String repo);
}
