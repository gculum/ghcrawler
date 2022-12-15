package com.ghcrawler.demo.domain.service;

import com.ghcrawler.demo.domain.model.branch.Branch;
import com.ghcrawler.demo.domain.model.branch.Commit;
import com.ghcrawler.demo.domain.model.repo.Owner;
import com.ghcrawler.demo.domain.model.repo.Repository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@SpringBootTest
class GithubFetcherTests {


	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	GithubFetcher gitFetcherMock = new GithubFetcher();


	/**
	 * used for fetcing of real data
	 */
	@Autowired
	GithubFetcher gitFetcher;

	/**
	 * Tests fetching of repositories
	 */
	@Test
	public void getMockedRepository() {

		/*
		Dummy repository data created for
		mock return value
		 */
		Repository repo = new Repository();
		repo.setOwner(new Owner());
		repo.getOwner().setLogin("aljinovic");
		Repository[] repos = {repo};

		Mockito.when(restTemplate.getForEntity("https://api.github.com/users/aljinovic/repos", Repository[].class))
          .thenReturn(new ResponseEntity(repos, HttpStatus.OK));

		//mocked query
		List<Repository> repoList = gitFetcherMock.getRepos("aljinovic");
		Assertions.assertEquals(repos[0], repoList.get(0));

		//case not covered with mock
		List<Repository> repoList2 = gitFetcherMock.getRepos("aljinovic2");
		Assertions.assertTrue(repoList2 == null);
	}


	/**
	 * Tests fetching of branches
	 */
	@Test
	public void getMockedBranch() {

		/*
		Dummy branch data created for
		mock return value
		 */
		Branch branch = new Branch();
		branch.setName("master");
		branch.setCommit(new Commit());
		branch.getCommit().setSha("cc97cc6cd069b0339da08d2fd39e22ecf7eff25f");
		Branch[] branches = {branch};

		Mockito.when(restTemplate.getForEntity("https://api.github.com/repos/aljinovic/kint/branches", Branch[].class))
				.thenReturn(new ResponseEntity(branches, HttpStatus.OK));

		//mocked query
		List<Branch> branchList = gitFetcherMock.getRepoBranches("aljinovic", "kint");
		Assertions.assertEquals(branches[0], branchList.get(0));

		//case not covered with mock
		List<Branch> branchList2 = gitFetcherMock.getRepoBranches("aljinovic2", "kint");
		Assertions.assertTrue(branchList2 == null);
	}


	/**
	 * Util method call for fetching real list of
	 * repositories.
	 * It is needed to set legitimate owner
	 */
	//@Test
	void getRepoList() {
		gitFetcher.getRepos("aljinovic").forEach(repo -> {
			System.out.println(repo.toString());
		});
	}


	/**
	 * Util method call for fetching real list of
	 * branches.
	 * It is needed to set legitimate owner and repo
	 */
	//@Test
	void getBranchList() {

		gitFetcher.getRepoBranches("aljinovic", "kint").forEach(branch -> {
			System.out.println(branch.toString());
		});
	}
}
