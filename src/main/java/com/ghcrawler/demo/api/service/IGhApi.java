package com.ghcrawler.demo.api.service;

import com.ghcrawler.demo.api.dto.Repo;
import java.util.List;

public interface IGhApi {

    /**
     * Method returns basic information about github repositories
     * that are owned by a user it specified login info
     *
     * @param username login info
     * @return list of repositories
     */
    List<Repo> getRepoData(String username);

}
