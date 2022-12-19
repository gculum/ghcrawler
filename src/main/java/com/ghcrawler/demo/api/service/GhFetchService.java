package com.ghcrawler.demo.api.service;

import com.ghcrawler.demo.api.dto.BranchDto;
import com.ghcrawler.demo.api.dto.RepoDto;
import com.ghcrawler.demo.domain.model.branch.Branch;
import com.ghcrawler.demo.domain.model.repo.Repository;
import com.ghcrawler.demo.domain.service.GithubFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

@Service 
public class GhFetchService implements IGhApi {

    @Autowired
    GithubFetcher gitFetcher;

    @Override
    public Flux<RepoDto> getRepoData(String username) {

        Flux<RepoDto> repoData = gitFetcher.getRepos(username).
                filter( i -> !i.getFork()).
                map(i -> {
            RepoDto myRepo = new RepoDto();
            myRepo.setName(i.getName());
            myRepo.setLogin(username);

            List<BranchDto> brList =gitFetcher.getRepoBranches(username, i.getName()).
                    stream().map(j -> {
                return new BranchDto(j.getName(), j.getCommit().getSha());
            }).collect(Collectors.toList());
            myRepo.setBranches(brList);

            return myRepo;
        });
        return repoData;
    }
}
