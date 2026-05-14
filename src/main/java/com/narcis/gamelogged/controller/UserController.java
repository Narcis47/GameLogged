package com.narcis.gamelogged.controller;

import com.narcis.gamelogged.Model.User;
import com.narcis.gamelogged.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    public record RegisterRequest(String username, String email, String password) {}

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        Optional<User> user = userService.getUserById(id);
        if(user.isPresent()){
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping()
    public ResponseEntity<Iterable<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request){
        boolean user = userService.registerUser(request.username(), request.email(), request.password());
        if(user){
            return ResponseEntity.ok("User Register Completed!");
        }
        return ResponseEntity.badRequest().body("Email already exists!");
    }
}
