package com.api.MeteorologicalData.security.config;

import com.api.MeteorologicalData.security.filter.JwtAuthenticationFilter;
import com.api.MeteorologicalData.security.jwt.JwtEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class HttpSecurityConfig {

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    JwtEntryPoint jwtEntryPoint;

    @Autowired
    AuthenticationProvider authenticationProvider;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf-> csrf.disable())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtEntryPoint))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                .requestMatchers("/auth/**","/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html").permitAll()
                                .anyRequest().authenticated()
                )
                .build();
    }
}
