package com.ghcrawler.demo.api.service;

import com.ghcrawler.demo.api.dto.Repo;
import com.ghcrawler.demo.domain.model.branch.Branch;
import com.ghcrawler.demo.domain.model.repo.Repository;
import com.ghcrawler.demo.domain.service.GithubFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service 
public class GhFetchService implements IGhApi {

    @Autowired
    GithubFetcher gitFetcher;

    @Override
    public Flux<Repo> getRepoData(String username) {

        Flux<Repo> repoData =
        gitFetcher.getRepos(username).filter( i -> !i.getFork()).
                map(i -> {
            Repo myRepo = new Repo();
            myRepo.setName(i.getName());
            myRepo.setLogin(username);

            //TODO consider calling async method and blocking it
            List<com.ghcrawler.demo.api.dto.Branch> brList =
                    gitFetcher.getRepoBranches(username, i.getName()).stream().map(j -> {
                return new com.ghcrawler.demo.api.dto.Branch(j.getName(), j.getCommit().getSha());
            }).collect(Collectors.toList());
            myRepo.setBranches(brList);


            return myRepo;
        });
        return repoData;
    }

/*
    public Flux<Repo> getRepoDataLATEST(String username) {

        Flux<Repo> repoData =
                gitFetcher.getRepos(username).map(i -> {
                    Repo myRepo = new Repo();
                    myRepo.setName(i.getName());
                    myRepo.setLogin(username);
                    gitFetcher.getRepoBranches(username, i.getName()).subscribe(j -> {
                        System.out.println("_X_ .");
                        myRepo.getBranches().add(new com.ghcrawler.demo.api.dto.Branch(j.getName(), j.getCommit().getSha()));
                    });
                    return myRepo;
                });
        return repoData;
    }
*/


    private Repo createRepoObject(Repository repository, com.ghcrawler.demo.domain.model.branch.Branch branch) {
        Repo repoObj = new Repo();
        repoObj.setName(repository.getName());
        repoObj.setLogin(repository.getOwner().getLogin());
        return repoObj;
    }
}
