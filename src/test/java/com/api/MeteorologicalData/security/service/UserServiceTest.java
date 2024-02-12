package com.api.MeteorologicalData.security.service;

import com.api.MeteorologicalData.security.entity.User;
import com.api.MeteorologicalData.security.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    private User user;

    @BeforeEach
    void setUp() {
        this.user = new User("User", "User@mail.com", "Username", "12345");
        when(userRepository.findByUsername(this.user.getUsername())).thenReturn(Optional.of(this.user));
        when(userRepository.existsByEmail(this.user.getEmail())).thenReturn(true);
        when(userRepository.existsByUsername(this.user.getUsername())).thenReturn(true);
        when(userRepository.save(user)).thenReturn(user);
    }

    @Test
    void getByUsernameFound() {
        Optional<User> user1 = userService.getByUsername(this.user.getUsername());
        assertAll(
                () -> assertNotNull(user1),
                () -> assertEquals(user1.get().getUsername(), this.user.getUsername())
        );
    }

    @Test
    void getByUsernameNotFound() {
        Optional<User> user1 = userService.getByUsername("doesNotExist");
        assertEquals(user1, Optional.empty());
    }

    @Test
    void existsByUsernameTrue() {
        assertTrue(userService.existsByUsername(this.user.getUsername()));
    }

    @Test
    void existsByUsernameFalse() {
        assertFalse(userService.existsByUsername("doesNotExist"));
    }

    @Test
    void existsByEmailTrue() {
        assertTrue(userService.existsByEmail(this.user.getEmail()));
    }

    @Test
    void existsByEmailFalse() {
        assertFalse(userService.existsByEmail("doesNotExist"));
    }

    @Test
    void getUserDetails() {
        assertNotNull(userService.getUserDetails(this.user.getUsername()));
    }

    @Test
    void save() {
        userService.save(this.user);
        verify(userRepository, times(1)).save(this.user);
    }

}