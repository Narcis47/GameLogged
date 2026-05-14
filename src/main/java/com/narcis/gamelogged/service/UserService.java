package com.narcis.gamelogged.service;

import com.narcis.gamelogged.Model.User;
import com.narcis.gamelogged.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean registerUser(String username, String email, String password){
        if(!userRepository.existsByEmail(email)){
            String hash = passwordEncoder.encode(password);
            User user = User.builder().username(username).passwordHash(hash).email(email).createdAt(LocalDateTime.now()).build();
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }
}
