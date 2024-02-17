package com.api.MeteorologicalData.security.config;

import com.api.MeteorologicalData.security.filter.JwtAuthenticationFilter;
import com.api.MeteorologicalData.security.jwt.JwtEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for Spring Security.
 */
@Configuration
public class HttpSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final JwtEntryPoint jwtEntryPoint;

    private final AuthenticationProvider authenticationProvider;

    /**
     * Constructor for the HttpSecurityConfig class.
     *
     * @param jwtAuthenticationFilter the JWT authentication filter
     * @param jwtEntryPoint           the JWT entry point
     * @param authenticationProvider  the authentication provider
     */
    public HttpSecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, JwtEntryPoint jwtEntryPoint, AuthenticationProvider authenticationProvider) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtEntryPoint = jwtEntryPoint;
        this.authenticationProvider = authenticationProvider;
    }

    /**
     * Creates a SecurityFilterChain bean that contains the necessary security configuration for the application.
     *
     * @return the SecurityFilterChain bean
     * @throws Exception if an error occurs while building the security filter chain
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtEntryPoint))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                .requestMatchers("/auth/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                                .anyRequest().authenticated()
                )
                .build();
    }
}
