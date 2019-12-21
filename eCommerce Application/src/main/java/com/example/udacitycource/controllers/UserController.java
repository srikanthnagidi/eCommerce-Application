package com.example.udacitycource.controllers;


import com.example.udacitycource.UdacityCourceApplication;
import com.example.udacitycource.model.persistence.Cart;
import com.example.udacitycource.model.persistence.User;
import com.example.udacitycource.model.persistence.repositories.CartRepository;
import com.example.udacitycource.model.persistence.repositories.UserRepository;
import com.example.udacitycource.model.requests.CreateUserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UdacityCourceApplication.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users.stream().sorted(Comparator
                .comparing(User::getUsername)
                .thenComparing(User::getId))
                .collect(Collectors.toList()));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return ResponseEntity.of(userRepository.findById(id));
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> findUserByName(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
        log.info("Creating User {}", createUserRequest.getUsername());
        User user = new User();
        user.setUsername(createUserRequest.getUsername());
        Cart cart = new Cart();
        cartRepository.save(cart);
        user.setCart(cart);
        if ((createUserRequest.getPassword().length() < 7) ||
                !createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())) {
            log.error("Error with user password. Cannot create user {}", createUserRequest.getUsername());
            return ResponseEntity.badRequest().build();
        }
        user.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()), true);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

}
