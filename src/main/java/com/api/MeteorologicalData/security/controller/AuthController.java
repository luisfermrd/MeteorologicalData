package com.api.MeteorologicalData.security.controller;

import com.api.MeteorologicalData.dto.Message;
import com.api.MeteorologicalData.security.dto.AuthResponse;
import com.api.MeteorologicalData.security.dto.LoginRequest;
import com.api.MeteorologicalData.security.dto.RegisterRequest;
import com.api.MeteorologicalData.security.service.AuthService;
import io.github.bucket4j.Bucket;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {


    private final AuthService authService;
    private Bucket bucket;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<Message>(
                    Message.builder().message("Validation error. Please verify the data provided.").build(),
                    HttpStatus.UNAUTHORIZED
            );
        }
        String token = authService.login(request);
        bucket = authService.limiterApi(request.getUsername());
        return new ResponseEntity<>(
                AuthResponse.builder()
                        .token(token)
                        .message("Successful login")
                        .build(),
                HttpStatus.ACCEPTED
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
        return authService.register(request);
    }


}
