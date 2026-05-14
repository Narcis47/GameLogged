package com.narcis.gamelogged.service;

import com.narcis.gamelogged.dto.RawgGameDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RawgService {
    private RestTemplate restTemplate;
    @Value("${rawg.api.key}")
    private String apiKey;

    public RawgService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RawgGameDto getGameById(Integer rawgId) {
        String url = "https://api.rawg.io/api/games/" + rawgId + "?key=" + apiKey;
        return restTemplate.getForObject(url, RawgGameDto.class);
    }

}
