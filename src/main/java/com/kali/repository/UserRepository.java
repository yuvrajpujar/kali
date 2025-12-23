package com.kali.repository;

import com.kali.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
// ...existing code...
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
