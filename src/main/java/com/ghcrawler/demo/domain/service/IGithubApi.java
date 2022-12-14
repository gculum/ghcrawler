package com.ghcrawler.demo.domain.service;

import com.ghcrawler.demo.domain.model.branch.Branch;
import com.ghcrawler.demo.domain.model.repo.Repository;

import java.util.List;

public interface IGithubApi {

    /**
     * Method fetches list of github repositories related
     * to the user specified by the login info
     *
     * @param username login info
     * @return list of repositories
     */
    List<Repository> getRepos(String username);


    /**
     * Method fetches list of github repository branches. Repository is
     * related to the user specified by the login info.
     *
     * @param username login info
     * @param repo name of the repository
     * @return list of repository branches
     */
    List<Branch> getRepoBranches(String username, String repo);
}
