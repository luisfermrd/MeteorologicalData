package com.api.MeteorologicalData.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AuthResponse class is used to return authentication response to the user.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    /**
     * Token is the JWT token that is used to authenticate the user.
     */
    private String token;
    /**
     * Message is the response message that is sent to the user.
     */
    private String message;
}
