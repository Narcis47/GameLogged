package com.narcis.gamelogged.repository;

import com.narcis.gamelogged.Model.UserGame;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserGameRepository extends CrudRepository<UserGame, Long> {
    List<UserGame> findByUserId(Long userId);
    Optional<UserGame> findByUserIdAndGameId(Long userId, Long gameId);
    boolean existsByUserIdAndGameId(Long userID, Long gameId);
}
