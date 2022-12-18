package com.ghcrawler.demo.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Repo {
    String name;

    String login;

    public Repo() {
        branches = new ArrayList<>();
    }
    List<Branch> branches;

    @Override
    public String toString() {
        String str = String.format("name:[%s], login:[%s]", name, login);

        List<String> brStrList = new ArrayList<>();
        for (Branch br : branches) {
            brStrList.add(String.format("{%s}", br.toString()));
        }

        str += String.format(", branches:{%s}", String.join(", ", brStrList));
        return str;
    }
}
