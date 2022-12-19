package com.ghcrawler.demo.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghcrawler.demo.domain.model.branch.Branch;
import com.ghcrawler.demo.domain.model.repo.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonUmarshaller {

    /**
     * Unmarshalls test case 1 JSON related to user aljinovic
     * Three repositories should be returned.
     *
     * @return list of Repository objects
     * @throws IOException
     */
    public static List<Repository> unmarshallRepoJsonTc1() throws IOException {
        List<Repository> repositories = new ArrayList<>();

        File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "tc1/repositories.json");
        String content = new String(Files.readAllBytes(file.toPath()));

        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Repository[] readValue = mapper.readValue(content, Repository[].class);
        repositories.addAll(Arrays.stream(readValue).toList());

        return repositories;
    }



    /**
     * Unmarshalls test case 1 JSON related to user aljinovic
     * branches will be unmarshalled for a repository defined
     * with input param
     *
     * @param repoId repository identification string
     * @return list of Branch objects
     * @throws IOException
     */
    public static List<Branch> unmarshallBranchJsonTc1(String repoId) throws IOException {
        List<Branch> branches = new ArrayList<>();

        File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + String.format("tc1/branches/%s.json", repoId));
        String content = new String(Files.readAllBytes(file.toPath()));

        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Branch[] readValue = mapper.readValue(content, Branch[].class);
        branches.addAll(Arrays.stream(readValue).toList());

        return branches;
    }
}
