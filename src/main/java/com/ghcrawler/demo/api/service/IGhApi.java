package com.ghcrawler.demo.api.service;

import com.ghcrawler.demo.api.dto.RepoDto;
import reactor.core.publisher.Flux;

public interface IGhApi {

    /**
     * Method returns basic information about Github repositories
     * that are owned by a user it specified login info
     *
     * @param username login info
     * @return list of repositories
     */
    Flux<RepoDto> getRepoData(String username);

}
