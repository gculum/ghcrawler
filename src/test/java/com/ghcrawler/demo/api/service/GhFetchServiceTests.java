package com.ghcrawler.demo.api.service;

import com.ghcrawler.demo.api.dto.RepoDto;
import com.ghcrawler.demo.domain.model.branch.Branch;
import com.ghcrawler.demo.domain.model.branch.Commit;
import com.ghcrawler.demo.domain.model.repo.Owner;
import com.ghcrawler.demo.domain.model.repo.Repository;
import com.ghcrawler.demo.domain.service.GithubFetcher;
import com.ghcrawler.demo.util.JsonUmarshaller;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@SpringBootTest
class GhFetchServiceTests {


	@Mock
	private GithubFetcher githubFetcherMock;

	@InjectMocks
	GhFetchService ghFetchServiceMock = new GhFetchService();


	/**
	 * used for fetcing of real data
	 */
	@Autowired
	GhFetchService ghFetchService;





	/**
	 * Tests fetching of repositories. Data is being mocked from the
	 * json tc1 dataset. Only 1 respository out of 3 is legitimate
	 * for this API call
	 */
	@Test
	public void getMockedRepository() {

		try {
			//mock GitHub repo fetch
			List<Repository> repos = JsonUmarshaller.unmarshallRepoJsonTc1();
			Assertions.assertEquals(3, repos.size());
			Mockito.when(githubFetcherMock.getRepos("aljinovic"))
					.thenReturn(Flux.just(repos.get(0), repos.get(1), repos.get(2)));

			//mock GitHub repo branches
			List<Branch> branches = JsonUmarshaller.unmarshallBranchJsonTc1("127411617");
			Mockito.when(githubFetcherMock.getRepoBranches("aljinovic", "grpc_vs_rest"))
					.thenReturn(new ArrayList<>(branches));

			//execute the call
			Flux<RepoDto> repoFlux = ghFetchServiceMock.getRepoData("aljinovic");

			//verify
			StepVerifier.create(repoFlux)
					.assertNext(i -> {
						Assertions.assertEquals("grpc_vs_rest", i.getName());
						Assertions.assertEquals("aljinovic", i.getLogin());
						Assertions.assertEquals(3 ,i.getBranches().size());
						Assertions.assertEquals("bence/cherry-picks" ,i.getBranches().get(0).getName());
						Assertions.assertEquals("960d3df58c75371359d234d03a6f1ada223eb10e" ,i.getBranches().get(0).getSha());
						Assertions.assertEquals("bence/cherry-picks-2" ,i.getBranches().get(1).getName());
						Assertions.assertEquals("a6a1026836ea6dbfbd5acf1d54222027fac43a26" ,i.getBranches().get(1).getSha());
						Assertions.assertEquals("master" ,i.getBranches().get(2).getName());
						Assertions.assertEquals("80ef0c845055faee5951439b47aa35aa556f2654" ,i.getBranches().get(2).getSha());
					}).
					verifyComplete();

		} catch (IOException e) {
			Assertions.assertFalse(true);
			throw new RuntimeException(e);
		}
	}





	/**
	 * Util method call for fetching real list of
	 * repositories.
	 */
	//@Test
	void getRepoData() throws InterruptedException {

		List<RepoDto> data = ghFetchService.getRepoData("aljinovic")
				.collectList()
				.block();
		data.forEach(System.out::println);
	}


}


