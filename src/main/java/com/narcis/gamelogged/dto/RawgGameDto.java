package com.narcis.gamelogged.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class RawgGameDto {
    private String name;

    @JsonProperty("background_image")
    private String backgroundImage;

    private Double rating;
}