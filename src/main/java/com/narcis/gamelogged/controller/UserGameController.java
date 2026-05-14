package com.narcis.gamelogged.controller;

import com.narcis.gamelogged.Model.UserGame;
import com.narcis.gamelogged.service.UserGameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/library")
public class UserGameController {
    private final UserGameService userGameService;
    public record RegisterRequest(Long userId, Long gameId, String status, Integer rating, String review){}
    public record UpdateStatusRequest(String status) {}
    public record UpdateRatingRequest(Integer rating) {}

    public UserGameController(UserGameService userGameService) {
        this.userGameService = userGameService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addGameToLibrary(@RequestBody RegisterRequest request){
        boolean userGame = userGameService.addGameToLibrary(request.userId(), request.gameId(), request.status());
        if(userGame){
            return ResponseEntity.ok("User Game Register Completed!");
        }
        return ResponseEntity.badRequest().body("User and Game already exists!");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<UserGame>> getLibraryByUserId(@PathVariable Long userId){
        List<UserGame> userGame = userGameService.getLibraryByUserId(userId);
        if(!userGame.isEmpty()){
            return ResponseEntity.ok(userGame);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> putStatusById(@PathVariable Long id, @RequestBody UpdateStatusRequest request){
        boolean userGame = userGameService.updateStatus(id, request.status());
        if (userGame){
            return ResponseEntity.ok().body("Status updated!");
        }
        return ResponseEntity.badRequest().body("Status could not be updated!");
    }

    @PutMapping("/{id}/rating")
    public ResponseEntity<String> putRatingById(@PathVariable Long id, @RequestBody UpdateRatingRequest request){
        boolean userGame = userGameService.updateRating(id, request.rating());
        if (userGame){
            return ResponseEntity.ok().body("Rating updated!");
        }
        return ResponseEntity.badRequest().body("Rating could not be updated!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        boolean userGame = userGameService.removeFromLibrary(id);
        if (userGame){
            return ResponseEntity.ok().body("Deleted!");
        }
        return ResponseEntity.badRequest().body("Cannot delete!");
    }
}
