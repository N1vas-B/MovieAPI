package com.example.movie.ticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.movie.ticket.dao.User;
import com.example.movie.ticket.dto.UserDto;
import com.example.movie.ticket.service.JwtService;
import com.example.movie.ticket.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    /**
     * User Registration Endpoint
     * @param userDto - User details
     * @return ResponseEntity with status message
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        // Check if the user already exists
        if (userService.findByEmail(userDto.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this email already exists.");
        }

        // Encrypt password before saving
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userService.saveUser(userDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
    }

    /**
     * User Login Endpoint
     * @param userDto - User login details
     * @return ResponseEntity with login status
     */
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserDto userDto) {
        User user = userService.findByEmail(userDto.getEmail());

        if (user == null || !passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
        }

        return ResponseEntity.ok("Login successful.");
    }

    /**
     * Login Test with JWT Token for Admin Only
     * @param userDto - User login details
     * @return ResponseEntity with login status and token (for admin only)
     */
    @PostMapping("/logintest")
    public ResponseEntity<?> loginUserTest(@RequestBody UserDto userDto) {
        User user = userService.findByEmail(userDto.getEmail());

        if (user == null) {
            user = userService.findByName(userDto.getName());
        }

        if (user == null || !passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
        }

        if ("ADMIN".equalsIgnoreCase(user.getRoles())) {
            String token = jwtService.generateToken(user.getName(), user.getRoles());
            return ResponseEntity.ok().body("{\"message\": \"Login successful\", \"token\": \"" + token + "\"}");
        } else {
            return ResponseEntity.ok("{\"message\": \"Login successful\"}");
        }
    }
}
