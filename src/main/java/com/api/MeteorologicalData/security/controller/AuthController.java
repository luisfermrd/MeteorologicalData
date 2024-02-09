package com.api.MeteorologicalData.security.controller;

import com.api.MeteorologicalData.dto.Message;
import com.api.MeteorologicalData.security.dto.LoginRequest;
import com.api.MeteorologicalData.security.dto.RegisterRequest;
import com.api.MeteorologicalData.security.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<Message>(
                    Message.builder().message("Validation error. Please verify the data provided.").build(),
                    HttpStatus.UNAUTHORIZED
            );
        }
        return authService.login(request);
    }

    @PostMapping("/register")
    public ResponseEntity<Message> register(@Valid @RequestBody RegisterRequest request, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<Message>(
                    Message.builder().message("Validation error. Please verify the data provided.").build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        return authService.register(request);
    }


}
