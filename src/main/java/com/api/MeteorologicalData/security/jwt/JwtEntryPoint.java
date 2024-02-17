package com.api.MeteorologicalData.security.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * This class is used to handle unauthorized access to resources that require authentication.
 * It implements the AuthenticationEntryPoint interface from Spring Security.
 * When an unauthorized request is made, this class will send a "401 Unauthorized" response.
 * The response body will contain a JSON object with an error message.
 */
@Component
@Slf4j
public class JwtEntryPoint implements AuthenticationEntryPoint {

    /**
     * This method is called when an unauthorized request is made to a resource that requires authentication.
     * It sends a "401 Unauthorized" response with an error message in the response body.
     * The error message is formatted as a JSON object.
     *
     * @param request       The HTTP request
     * @param response      The HTTP response
     * @param authException The exception that was thrown during authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("Error en el metodo commence");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}
