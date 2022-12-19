package com.ghcrawler.demo.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RepoDto {
    String name;

    String login;

    public RepoDto() {
        branches = new ArrayList<>();
    }
    List<BranchDto> branches;

    @Override
    public String toString() {
        String str = String.format("name:[%s], login:[%s]", name, login);

        List<String> brStrList = new ArrayList<>();
        for (BranchDto br : branches) {
            brStrList.add(String.format("{%s}", br.toString()));
        }

        str += String.format(", branches:{%s}", String.join(", ", brStrList));
        return str;
    }
}
