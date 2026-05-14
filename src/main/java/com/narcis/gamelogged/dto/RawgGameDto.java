package com.narcis.gamelogged.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class RawgGameDto {
    private Integer id;
    private String name;
    private Double rating;

    @JsonProperty("background_image")
    @JsonAlias("background_image")
    private String backgroundImage;
}