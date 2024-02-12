package com.api.MeteorologicalData.security.controller;

import com.api.MeteorologicalData.dto.Message;
import com.api.MeteorologicalData.security.dto.AuthResponse;
import com.api.MeteorologicalData.security.dto.LoginRequest;
import com.api.MeteorologicalData.security.dto.RegisterRequest;
import com.api.MeteorologicalData.security.entity.User;
import com.api.MeteorologicalData.security.jwt.JwtService;
import com.api.MeteorologicalData.security.service.AuthService;
import com.api.MeteorologicalData.security.service.UserService;
import io.github.bucket4j.Bucket;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {


    private final AuthService authService;
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private Bucket bucket;

    public AuthController(AuthService authService, UserService userService, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<Message>(
                    Message.builder().message("Validation error. Please verify the data provided.").build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        if (!userService.existsByUsername(request.getUsername())) {
            return new ResponseEntity<Message>(
                    Message.builder().message("Validation error. Please verify the data provided.").build(),
                    HttpStatus.UNAUTHORIZED
            );
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails userDetails = userService.getUserDetails(request.getUsername());
        String token = jwtService.getToken(userDetails);
        bucket = authService.limiterApi(request.getUsername());
        return new ResponseEntity<>(
                AuthResponse.builder()
                        .token(token)
                        .message("Successful login")
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping("/register")
    public ResponseEntity<Message> register(@Valid @RequestBody RegisterRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<Message>(
                    Message.builder().message("Validation error. Please verify the data provided.").build(),
                    HttpStatus.BAD_REQUEST
            );
        }

        if (userService.existsByUsername(request.getUsername())) {
            return new ResponseEntity<Message>(
                    Message.builder().message("The username: " + request.getUsername() + ", is already registered").build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        if (userService.existsByEmail(request.getEmail())) {
            return new ResponseEntity<Message>(
                    Message.builder().message("The email: " + request.getEmail() + ", is already registered").build(),
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


}
