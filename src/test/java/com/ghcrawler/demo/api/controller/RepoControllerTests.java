package com.ghcrawler.demo.api.controller;

import com.ghcrawler.demo.api.dto.BranchDto;
import com.ghcrawler.demo.api.dto.RepoDto;
import com.ghcrawler.demo.api.service.GhFetchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RepoController.class)
public class RepoControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private GhFetchService ghFetchService;

    /**
     * Testing of RepoController controller module
     *
     * @throws Exception
     */
    @Test
    public void getAllRepositories() throws Exception
    {
        List<RepoDto> repositoryList = mockRepoDtoListForAljinovic();
        when(ghFetchService.getRepoData("aljinovic")).thenReturn(Flux.just(repositoryList.get(0)));

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/repo/aljinovic")
                        .accept(MediaType.APPLICATION_STREAM_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.login").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.branches").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.branches[*].sha").isNotEmpty());
    }


    /**
     * Mock factory method used by controller test
     * method
     *
     * @return list of RepoDto objects
     */
    private List<RepoDto> mockRepoDtoListForAljinovic() {
        List<RepoDto> repositoryList = new ArrayList<>();
        RepoDto repo1 = new RepoDto();
        repo1.setName("grpc_vs_rest");
        repo1.setLogin("aljinovic");

        List<BranchDto> branches = new ArrayList<>();
        branches.add(new BranchDto("bence/cherry-picks", "960d3df58c75371359d234d03a6f1ada223eb10e"));
        branches.add(new BranchDto("bence/cherry-picks-2", "a6a1026836ea6dbfbd5acf1d54222027fac43a26"));
        branches.add(new BranchDto("master", "80ef0c845055faee5951439b47aa35aa556f2654"));
        repo1.setBranches(branches);

        repositoryList.add(repo1);
        return repositoryList;

    }
}
