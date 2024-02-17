package com.api.MeteorologicalData.security.controller;

import com.api.MeteorologicalData.dto.Message;
import com.api.MeteorologicalData.dto.MessageBindingResult;
import com.api.MeteorologicalData.security.dto.AuthResponse;
import com.api.MeteorologicalData.security.dto.LoginRequest;
import com.api.MeteorologicalData.security.dto.RegisterRequest;
import com.api.MeteorologicalData.security.entity.User;
import com.api.MeteorologicalData.security.jwt.JwtService;
import com.api.MeteorologicalData.security.service.AuthService;
import com.api.MeteorologicalData.security.service.UserService;
import io.github.bucket4j.Bucket;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;


/**
 * The AuthController class is responsible for handling all authentication-related requests.
 * It provides endpoints for registering new users, logging in, and obtaining authentication tokens.
 * The controller uses the AuthService and UserService classes to perform operations on the database.
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin
@Tag(name = "Authentication", description = "Operations related to user registration and login.")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private Bucket bucket;

    /**
     * The constructor takes dependencies for the AuthService, UserService, and JwtService classes,
     * as well as the AuthenticationManager.
     */
    public AuthController(AuthService authService, UserService userService, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    /**
     * The login method is used to authenticate a user using their username and password.
     * If the authentication is successful, a JSON Web Token (JWT) is generated and returned to the user.
     * If the authentication fails, an error message is returned.
     *
     * @param request       a LoginRequest object containing the username and password of the user
     * @param bindingResult a BindingResult object that contains any validation errors
     * @return a ResponseEntity object containing an AuthResponse object with the JWT and a message
     */
    @Operation(
            summary = "Get authentication token.",
            description = "The authentication token is obtained through the username and password.",
            responses = {
                    @ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
                    @ApiResponse(responseCode = "200", description = "Successful login", content = @Content)
            }
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request, BindingResult bindingResult) {
        ResponseEntity<?> validationErrors = getResponseEntity(bindingResult);
        if (validationErrors != null) return validationErrors;
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

    /**
     * The register method is used to register a new user.
     * It requires the user to provide their name, email, username, and password.
     * If the user already exists, an error message is returned.
     * If the registration is successful, a message is returned indicating that the user was registered.
     *
     * @param request       a RegisterRequest object containing the user's information
     * @param bindingResult a BindingResult object that contains any validation errors
     * @return a ResponseEntity object containing a Message object with a message indicating the status of the registration
     */
    @Operation(
            summary = "Register a user.",
            description = "You must provide your name, email, username and password information to register..",
            responses = {
                    @ApiResponse(responseCode = "400", description = "Not found", content = @Content),
                    @ApiResponse(responseCode = "200", description = "Registered user successfully", content = @Content)
            }
    )
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request, BindingResult bindingResult) {
        ResponseEntity<?> validationErrors = getResponseEntity(bindingResult);
        if (validationErrors != null) return validationErrors;

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

    /**
     * This method is used to return any validation errors that may have occurred during the request.
     * If validation errors exist, a ResponseEntity object with a MessageBindingResult object is returned,
     * containing the error messages and the fields that caused the errors.
     * If no validation errors exist, null is returned.
     *
     * @param bindingResult a BindingResult object that contains any validation errors
     * @return a ResponseEntity object containing a MessageBindingResult object with any validation errors, or null if no errors occurred
     */
    private ResponseEntity<?> getResponseEntity(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> validationErrors = bindingResult.getAllErrors().stream()
                    .filter(error -> error instanceof FieldError)
                    .map(error -> (FieldError) error)
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            return new ResponseEntity<MessageBindingResult>(
                    MessageBindingResult.builder()
                            .message("Validation error. Please verify the data provided.")
                            .errors(validationErrors)
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        return null;
    }
}
