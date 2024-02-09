package com.api.MeteorologicalData.security.service;

import com.api.MeteorologicalData.dto.Message;
import com.api.MeteorologicalData.security.dto.AuthResponse;
import com.api.MeteorologicalData.security.dto.LoginRequest;
import com.api.MeteorologicalData.security.dto.RegisterRequest;
import com.api.MeteorologicalData.security.entity.User;
import com.api.MeteorologicalData.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {
    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    public ResponseEntity<AuthResponse> login(LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        UserDetails userDetails = userService.getUserDetails(request.getUsername());
        String token = jwtService.getToken(userDetails);
        return new ResponseEntity<>(
                AuthResponse.builder()
                        .token(token)
                        .message("Successful login")
                        .build(),
                HttpStatus.ACCEPTED
        );
    }

    public ResponseEntity<Message> register(RegisterRequest request){
        if (userService.existsByUsername(request.getUsername())){
            return new ResponseEntity<Message>(
                    Message.builder().message("The username: "+request.getUsername()+", is already registered").build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        if (userService.existsByEmail(request.getEmail())){
            return new ResponseEntity<Message>(
                    Message.builder().message("The email: "+request.getUsername()+", is already registered").build(),
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
