package com.book.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.store.dao.User;
import com.book.store.dto.UserDto;
import com.book.store.service.JwtService;
import com.book.store.service.Userservice;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private Userservice userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtService jwtService;

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
		// Check if user already exists
		User existingUser = userService.findByName(userDto.getEmail());
		if (existingUser != null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this email already exists.");
		}

		// Encode the password
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

		// Save the user
		userService.saveUser(userDto);

		return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
	}

	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody UserDto userDto) {
		// Find user by email
		User user = userService.findByEmail(userDto.getEmail());
		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
		} // Verify password
		if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
		} // Simulate generating a token or response after successful login
		return ResponseEntity.ok("Login successful.");
	}

	@PostMapping("/logintest")
	public ResponseEntity<String> loginUserTest(@RequestBody UserDto userDto) {
		User user = userService.findByName(userDto.getName());
		if (user == null || !passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
		}
		if ("ADMIN".equals(user.getRoles())) {
			String token = jwtService.generateToken(user.getName(), user.getRoles());
			return ResponseEntity.ok("Login successful. Token: " + token);
		} else {
			return ResponseEntity.ok("Login successful.");
		}
	}

}
