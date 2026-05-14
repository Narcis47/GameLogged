package com.narcis.gamelogged.service;

import com.narcis.gamelogged.dto.RawgGameDto;
import com.narcis.gamelogged.dto.RawgSearchDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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

    public List<RawgGameDto> searchGames(String query){
        String url = "https://api.rawg.io/api/games?search=" + query + "&key=" + apiKey + "&page_size=5";
        RawgSearchDto response = restTemplate.getForObject(url, RawgSearchDto.class);
        return response.getResults();
    }

}
