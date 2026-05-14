package com.narcis.gamelogged.controller;

import com.narcis.gamelogged.Model.Game;
import com.narcis.gamelogged.dto.RawgGameDto;
import com.narcis.gamelogged.service.GameService;
import com.narcis.gamelogged.service.RawgService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/games")
public class GameController {
    private final GameService gameService;
    private final RawgService rawgService;

    public GameController(GameService gameService, RawgService rawgService) {
        this.gameService = gameService;
        this.rawgService = rawgService;
    }

    public record AddGameRequest(Integer rawgId) {}



    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable Long id){
        Optional<Game> game = gameService.getGameById(id);
        if(game.isPresent()){
            return ResponseEntity.ok(game.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<Iterable<Game>> getAllGames(){
        return ResponseEntity.ok(gameService.getAllGames());
    }

    @PostMapping("/add")
    public ResponseEntity<String> addGame(@RequestBody AddGameRequest request){
        boolean game = gameService.addGame(request.rawgId());
        if (game){
            return ResponseEntity.ok("Game added!");
        }
        return ResponseEntity.badRequest().body("Game already added");
    }

    @GetMapping("/rawg/{rawgId}")
    public ResponseEntity<RawgGameDto> getRawgGame(@PathVariable Integer rawgId){
        return ResponseEntity.ok(rawgService.getGameById(rawgId));
    }
}
