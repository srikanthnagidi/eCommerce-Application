package com.example.udacitycource.model.persistence.repositories;

import com.example.udacitycource.model.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
