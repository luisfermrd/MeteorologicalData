package com.api.MeteorologicalData.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for registering a new user.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    /**
     * The user's full name.
     */
    @NotBlank
    private String name;

    /**
     * The user's email address.
     */
    @NotBlank
    @Email
    private String email;

    /**
     * The user's username.
     */
    @NotBlank
    private String username;

    /**
     * The user's password.
     */
    @NotBlank
    private String password;

}
