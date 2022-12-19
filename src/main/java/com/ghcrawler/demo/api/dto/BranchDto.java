package com.ghcrawler.demo.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BranchDto {
    String name;
    String sha;

    @Override
    public String toString() {
        return String.format("name:[%s], sha:[%s]", name, sha);
    }
}
