package com.example.movie.ticket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.movie.ticket.filter.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(UserDetailsService userDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Disable CSRF for APIs
            .authorizeHttpRequests(auth -> auth
                // 🔹 Public Endpoints (No authentication required)
                .requestMatchers("/api/user/register", "/api/user/login", "/api/user/logintest",
                        "/api/movies/getById/**", "/api/movies/allmovies","/api/bookings/bookticket","/api/bookings/{id}",
                        "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()

                // 🔹 ADMIN Restricted Endpoints (Only Admin can manage movies & bookings)
                .requestMatchers("/api/movies/create", "/api/movies/update/**", "/api/movies/delete/**").hasRole("ADMIN")
                .requestMatchers("/api/bookings/bookedmovieslist").hasRole("ADMIN") 
                .requestMatchers("/api/bookings/{id}/status").hasRole("ADMIN") 

                // 🔹 CUSTOMER Restricted Endpoints (Only Customers can book tickets)
               // .requestMatchers("/api/bookings/bookticket").hasRole("USER") 

                // 🔹 Any other request must be authenticated
                .anyRequest().authenticated())

            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) 
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); 

        return http.build();
    }
}
