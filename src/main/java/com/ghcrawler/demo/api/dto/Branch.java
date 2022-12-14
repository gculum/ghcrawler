package com.ghcrawler.demo.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Branch {
    String name;
    String sha;
}
