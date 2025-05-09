package com.example.karaoke.user_service.Repository;

import com.example.karaoke.user_service.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUsernameAndPassword(String username, String password);
}
