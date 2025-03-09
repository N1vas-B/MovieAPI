package com.example.movie.ticket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.movie.ticket.dao.User;
import com.example.movie.ticket.repository.UserRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with name: " + name));
        
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getName()) // Use name for username
                .password(user.getPassword())
                .roles(user.getRoles().split(",")) // Split roles by comma
                .build();
    }
}

