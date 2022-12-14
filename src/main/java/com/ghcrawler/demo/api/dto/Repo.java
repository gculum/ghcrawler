package com.ghcrawler.demo.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Repo {
    String name;

    String login;

    List<Branch> branches = new ArrayList<>();
}
