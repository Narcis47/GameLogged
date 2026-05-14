package com.narcis.gamelogged.service;

import com.narcis.gamelogged.Model.UserGame;
import com.narcis.gamelogged.repository.UserGameRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserGameService {
    private final UserGameRepository gameRepository;

    public UserGameService(UserGameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public boolean addGameToLibrary(Long userId, Long gameId, String status){
        if(!gameRepository.existsByUserIdAndGameId(userId,gameId)){
            UserGame userGame = UserGame.builder().userId(userId).gameId(gameId).status(status).createdAt(LocalDateTime.now()).build();
            gameRepository.save(userGame);
            return true;
        }
        return false;
    }

    public List<UserGame> getLibraryByUserId(Long userId){
        return gameRepository.findByUserId(userId);
    }

    public boolean updateStatus(Long id,String status){
        Optional<UserGame> userGame = gameRepository.findById(id);
        if(userGame.isEmpty()){
            return false;
        }
        userGame.get().setStatus(status);
        gameRepository.save(userGame.get());
        return true;
    }

    public boolean updateRating(Long id,Integer rating){
        Optional<UserGame> userGame = gameRepository.findById(id);
        if(userGame.isEmpty() || rating < 1 || rating > 10){
            return false;
        }
        userGame.get().setRating(rating);
        gameRepository.save(userGame.get());
        return true;
    }

    public boolean removeFromLibrary(Long id){
        if(gameRepository.existsById(id)){
            gameRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
