package com.example.udacitycource.controllers;

import com.example.udacitycource.model.persistence.Cart;
import com.example.udacitycource.model.persistence.Item;
import com.example.udacitycource.model.persistence.User;
import com.example.udacitycource.model.persistence.repositories.CartRepository;
import com.example.udacitycource.model.persistence.repositories.ItemRepository;
import com.example.udacitycource.model.persistence.repositories.UserRepository;
import com.example.udacitycource.model.requests.ModifyCartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired private ItemRepository itemRepository;

    @PostMapping("/addToCart")
    public ResponseEntity<Cart> addToCart(@RequestBody ModifyCartRequest request){
        User user = userRepository.findByUsername(request.getUsername());
        if (user==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Optional<Item> optionalItem = itemRepository.findById(request.getItemId());
        if(!optionalItem.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Cart cart = user.getCart();
        IntStream.range(0, request.getQuantity()).forEach(i -> cart.addItem(optionalItem.get()));
        cartRepository.save(cart);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/{username}/cart")
    public ResponseEntity<Cart> getCartItems(@PathVariable String username){
        User user = userRepository.findByUsername(username);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user.getCart());
    }
}
