package com.narcis.gamelogged.repository;

import com.narcis.gamelogged.Model.Game;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GameRepository extends CrudRepository<Game, Long> {
    Optional<Game> findByRawgId(Integer rawgId);
}
