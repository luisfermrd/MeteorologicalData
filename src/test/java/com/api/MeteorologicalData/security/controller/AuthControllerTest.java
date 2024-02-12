package com.api.MeteorologicalData.security.controller;

import com.api.MeteorologicalData.security.dto.LoginRequest;
import com.api.MeteorologicalData.security.dto.RegisterRequest;
import com.api.MeteorologicalData.security.entity.User;
import com.api.MeteorologicalData.security.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@WebAppConfiguration
class AuthControllerTest {

    private final static String BASE_URL = "/auth";
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;
    private User user;
    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.user = new User("User", "User@mail.com", "Username", passwordEncoder.encode("12345"));
        this.registerRequest = RegisterRequest.builder().name("User2").email("User2@mail.com").username("Username2").password("12345").build();
        this.loginRequest = LoginRequest.builder().username("Username").password("12345").build();
        when(userRepository.findByUsername(this.user.getUsername())).thenReturn(Optional.of(this.user));
        when(userRepository.existsByEmail(this.user.getEmail())).thenReturn(true);
        when(userRepository.existsByUsername(this.user.getUsername())).thenReturn(true);
        when(userRepository.save(user)).thenReturn(user);
    }

    @Test
    void login() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL.concat("/login"))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(this.loginRequest))
                )
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void loginWithoutParameter() throws Exception {
        this.loginRequest.setUsername("");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL.concat("/login"))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(this.loginRequest))
                )
                .andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    void loginWithoutExistingUser() throws Exception {
        this.loginRequest.setUsername("NotExists");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL.concat("/login"))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(this.loginRequest))
                )
                .andReturn();
        assertEquals(401, result.getResponse().getStatus());
    }

    @Test
    void register() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL.concat("/register"))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(this.registerRequest))
                )
                .andReturn();
        assertEquals(201, result.getResponse().getStatus());
    }

    @Test
    void registerWithoutParameter() throws Exception {
        this.registerRequest.setUsername("");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL.concat("/register"))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(this.registerRequest))
                )
                .andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    void registerUsernameExists() throws Exception {
        this.registerRequest.setUsername(this.user.getUsername());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL.concat("/register"))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(this.registerRequest))
                )
                .andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    void registerEmailExists() throws Exception {
        this.registerRequest.setEmail(this.user.getEmail());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL.concat("/register"))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapToJson(this.registerRequest))
                )
                .andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}