package com.narcis.gamelogged.service;

import com.narcis.gamelogged.Model.Game;
import com.narcis.gamelogged.repository.GameRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository){
        this.gameRepository = gameRepository;
    }

    public boolean addGame(Integer rawgId){
        if(gameRepository.findByRawgId(rawgId).isEmpty()){
            Game game = Game.builder().rawgId(rawgId).build();
            gameRepository.save(game);
            return true;
        }
        return false;
    }

    public Optional<Game> getGameById(Long id){
        return gameRepository.findById(id);
    }

    public Iterable<Game> getAllGames(){
        return gameRepository.findAll();
    }
}
