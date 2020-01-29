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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class SignUpController {

    private static final Logger log = LoggerFactory.getLogger(UdacityCourceApplication.class);
    @Autowired private CartRepository cartRepository;
    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired private UserRepository userRepository;

    @PostMapping("/sign-up")
    public ResponseEntity<?> addUser(@RequestBody CreateUserRequest createUserRequest){
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
        user.setPassword(createUserRequest.getPassword(), true);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
