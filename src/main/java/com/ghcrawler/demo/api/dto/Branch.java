package com.ghcrawler.demo.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Branch {
    String name;
    String sha;

    @Override
    public String toString() {
        return String.format("name:[%s], sha:[%s]", name, sha);
    }
}
