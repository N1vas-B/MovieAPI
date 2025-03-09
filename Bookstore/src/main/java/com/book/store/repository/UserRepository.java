package com.book.store.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.book.store.dao.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    
    Optional<User> findByName(String email);
}