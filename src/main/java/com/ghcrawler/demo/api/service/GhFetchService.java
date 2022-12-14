package com.ghcrawler.demo.api.service;

import com.ghcrawler.demo.api.dto.Branch;
import com.ghcrawler.demo.api.dto.Repo;
import com.ghcrawler.demo.domain.model.repo.Repository;
import com.ghcrawler.demo.domain.service.GithubFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service 
public class GhFetchService implements IGhApi {

    @Autowired
    GithubFetcher gitFetcher;

    @Override
    public List<Repo> getRepoData(String username) {

        List<Repository> repos = gitFetcher.getRepos(username);

        List<Repo> repoList = new ArrayList<>();
        repos.forEach(repo -> {
            if(!repo.getFork()) {
                Repo repoItem = new Repo();
                repoItem.setName(repo.getName());
                repoItem.setLogin(username);
                gitFetcher.getRepoBranches(username, repo.getName()).forEach(branch -> {
                    repoItem.getBranches().add(new Branch(branch.getName(), branch.getCommit().getSha()));
                });
                repoList.add(repoItem);
            }
        });

        return repoList;
    }
}
