package com.api.MeteorologicalData.security.service;

import com.api.MeteorologicalData.dto.Message;
import com.api.MeteorologicalData.security.dto.LoginRequest;
import com.api.MeteorologicalData.security.dto.RegisterRequest;
import com.api.MeteorologicalData.security.entity.User;
import com.api.MeteorologicalData.security.jwt.JwtService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Slf4j
@Service
@Transactional
public class AuthService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserService userService, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public String login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails userDetails = userService.getUserDetails(request.getUsername());
        return jwtService.getToken(userDetails);

    }

    public ResponseEntity<Message> register(RegisterRequest request) {
        if (userService.existsByUsername(request.getUsername())) {
            return new ResponseEntity<Message>(
                    Message.builder().message("The username: " + request.getUsername() + ", is already registered").build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        if (userService.existsByEmail(request.getEmail())) {
            return new ResponseEntity<Message>(
                    Message.builder().message("The email: " + request.getUsername() + ", is already registered").build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        User user = new User(
                request.getName(),
                request.getEmail(),
                request.getUsername(),
                passwordEncoder.encode(request.getPassword())
        );
        userService.save(user);
        return new ResponseEntity<Message>(
                Message.builder().message("Registered user successfully").build(),
                HttpStatus.CREATED
        );
    }

    @Cacheable(value = "limiterApi", key = "#username")
    public Bucket limiterApi(String username) {
        Bandwidth limit = Bandwidth.classic(100, Refill.intervally(100, Duration.ofHours(1)));
        return Bucket.builder().addLimit(limit).build();
    }
}
