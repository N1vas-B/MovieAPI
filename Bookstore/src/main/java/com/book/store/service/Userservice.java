package com.book.store.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.book.store.dao.User;
import com.book.store.dto.UserDto;
import com.book.store.repository.UserRepository;

@Service
public class Userservice {

    @Autowired
    private UserRepository userRepository;

    
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    
    public User findByName(String name) {
        return userRepository.findByName(name).orElse(null);
    }
    
    public void saveUser(UserDto userDto) {
        // Map UserDto to User
        User user = new User();
        user.setPassword(userDto.getPassword());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setRoles(userDto.getRoles());
        userRepository.save(user);
    }

   
   
}
