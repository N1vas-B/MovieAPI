package com.example.movie.ticket.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.movie.ticket.dao.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByName(String name);
}
