package com.ghcrawler.demo.domain.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghcrawler.demo.domain.model.branch.Branch;
import com.ghcrawler.demo.domain.model.repo.Repository;
import com.ghcrawler.demo.util.JsonUmarshaller;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@Service
@SpringBootTest
class GithubFetcherTests {


	@Mock
	private RestTemplate restTemplateMock;



	/**
	 * used for fetcing of real data
	 */
	@Autowired
	GithubFetcher gitFetcher;



	@Mock
	private WebClient webClientMock;

	@Mock
	private WebClient.RequestHeadersSpec requestHeadersSpecMock;

	@Mock
	private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;

	@Mock
	private WebClient.ResponseSpec responseSpecMock;



	@InjectMocks
	GithubFetcher gitFetcherMock = new GithubFetcher();




	/**
	 * Tests fetching of repositories
	 */
	@Test
	public void getMockedRepository() {

		try {
			List<Repository> repos = JsonUmarshaller.unmarshallRepoJsonTc1();
			Assertions.assertEquals(3, repos.size());

			Mockito.when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
			Mockito.when(requestHeadersUriSpecMock.uri(Mockito.anyString())).thenReturn(requestHeadersSpecMock);
			Mockito.when(requestHeadersSpecMock.header(Mockito.anyString(), Mockito.anyString())).thenReturn(requestHeadersSpecMock);
			Mockito.when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
			Mockito.when(responseSpecMock.bodyToFlux(
					ArgumentMatchers.<Class<Repository>>notNull())).thenReturn(Flux.just(repos.get(0), repos.get(1), repos.get(2)));

			Flux<Repository> repoList = gitFetcherMock.getRepos("aljinovic");


			StepVerifier.create(repoList)
					.assertNext(i -> {
						Assertions.assertTrue(i.getName().equals("aiohttp-datadog"));
						Assertions.assertTrue(i.getFork().equals(true));
						Assertions.assertTrue(i.getOwner().getLogin().equals("aljinovic"));
					}).assertNext(i -> {
						Assertions.assertTrue(i.getName().equals("aiohttp-sentry"));
						Assertions.assertTrue(i.getFork().equals(true));
						Assertions.assertTrue(i.getOwner().getLogin().equals("aljinovic"));
					}).assertNext(i -> {
						Assertions.assertTrue(i.getName().equals("grpc_vs_rest"));
						Assertions.assertTrue(i.getFork().equals(false));
						Assertions.assertTrue(i.getOwner().getLogin().equals("aljinovic"));
					}).
					verifyComplete();

		} catch (IOException e) {
			Assertions.assertFalse(true);
			throw new RuntimeException(e);
		}
	}




	/**
	 * Tests fetching of branches related to grpc_vs_rest repository
	 * belonging to user aljinovic. Test case is based on a JSON files
	 * located inside tc1 resource location
	 */
	@Test
	public void getMockedBranch() {

		try {
			List<Branch> branches = JsonUmarshaller.unmarshallBranchJsonTc1("127411617");
			Assertions.assertEquals(3, branches.size());

			Branch[] brArray = new Branch[branches.size()];
			branches.toArray(brArray);
			ResponseEntity<Branch[]> response = new ResponseEntity(brArray, HttpStatus.OK);
			Mockito.when(restTemplateMock.exchange(Mockito.eq("https://api.github.com/repos/aljinovic/grpc_vs_rest/branches"),
							Mockito.any(), Mockito.any(), Mockito.eq(Branch[].class)))
					.thenReturn(response);



			//mocked query
			List<Branch> branchList = gitFetcherMock.getRepoBranches("aljinovic", "grpc_vs_rest");
			Assertions.assertEquals(3, branchList.size());
			Assertions.assertEquals(branches.get(0), branchList.get(0));

			//case not covered with mock
			List<Branch> branchList2 = gitFetcherMock.getRepoBranches("aljinovic2", "kint");
			Assertions.assertTrue(branchList2 == null);

		} catch (IOException e) {
			Assertions.assertFalse(true);
			throw new RuntimeException(e);
		}
	}


	/**
	 * Util method call for fetching real list of
	 * repositories.
	 * It is needed to get legitimate owner
	 */
	//@Test
	void getRepoList() throws InterruptedException {
		Flux<Repository> flux = gitFetcher.getRepos("aljinovic");
		List<Repository> data = flux.collectList().block();
		data.forEach(System.out::println);
	}


	/**
	 * Util method call for fetching real list of
	 * branches.
	 * It is needed to get legitimate owner and repo
	 */
	//@Test
	void getBranchList() {
		List<Branch> br = gitFetcher.getRepoBranches("aljinovic", "kint");
		gitFetcher.getRepoBranches("aljinovic", "kint").forEach(System.out::println);
	}

}
