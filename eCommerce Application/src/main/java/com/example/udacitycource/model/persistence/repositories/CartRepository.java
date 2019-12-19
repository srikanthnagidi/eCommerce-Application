package com.example.udacitycource.model.persistence.repositories;

import com.example.udacitycource.model.persistence.Cart;
import com.example.udacitycource.model.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
}
