package com.example.udacitycource.controllers;

import com.example.udacitycource.model.persistence.Cart;
import com.example.udacitycource.model.persistence.User;
import com.example.udacitycource.model.persistence.UserOrder;
import com.example.udacitycource.model.persistence.repositories.OrderRepository;
import com.example.udacitycource.model.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController("/api/order")
public class OrderController {

    @Autowired private UserRepository userRepository;

    @Autowired private OrderRepository orderRepository;

    @PostMapping("/submit/{username}")
    public ResponseEntity<UserOrder> submit(@PathVariable String username){
        //System.out.println("in post mapping");
        User user =userRepository.findByUsername(username);
        System.out.println(user.getUsername());
        if (user == null){
            return ResponseEntity.notFound().build();
        }
        UserOrder order = UserOrder.createFromCart(user.getCart());
        orderRepository.save(order);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/history/{username}")
    public ResponseEntity<List<UserOrder>> getOrdersForUser(@PathVariable String username){
        User user = userRepository.findByUsername(username);
        if (user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderRepository.findByUser(user));
    }
}
