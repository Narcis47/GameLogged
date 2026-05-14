package com.narcis.gamelogged.controller;

import com.narcis.gamelogged.Model.Game;
import com.narcis.gamelogged.dto.RawgGameDto;
import com.narcis.gamelogged.service.GameService;
import com.narcis.gamelogged.service.RawgService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<Game> addGame(@RequestBody AddGameRequest request){
        return ResponseEntity.ok(gameService.addGame(request.rawgId()));
    }

    @GetMapping("/rawg/{rawgId}")
    public ResponseEntity<RawgGameDto> getRawgGame(@PathVariable Integer rawgId){
        return ResponseEntity.ok(rawgService.getGameById(rawgId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<RawgGameDto>> searchGames(@RequestParam String query) {
        return ResponseEntity.ok(rawgService.searchGames(query));
    }

    @GetMapping("/{id}/rawg")
    public ResponseEntity<RawgGameDto> getRawgDataByGameId(@PathVariable Long id){
        return gameService.getGameById(id)
                .map(game -> ResponseEntity.ok(rawgService.getGameById(game.getRawgId())))
                .orElse(ResponseEntity.notFound().build());
    }
}
