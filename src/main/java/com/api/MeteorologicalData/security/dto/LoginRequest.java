package com.api.MeteorologicalData.security.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Login Request
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    /**
     * The username of the user
     */
    @NotBlank
    private String username;

    /**
     * The password of the user
     */
    @NotBlank
    private String password;

}
