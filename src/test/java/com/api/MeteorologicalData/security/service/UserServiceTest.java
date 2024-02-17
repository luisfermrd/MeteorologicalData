package com.api.MeteorologicalData.security.service;

import com.api.MeteorologicalData.security.entity.User;
import com.api.MeteorologicalData.security.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    /**
     * Mocked UserRepository
     */
    @MockBean
    private UserRepository userRepository;

    /**
     * Autowired UserService
     */
    @Autowired
    private UserService userService;

    /**
     * Mocked User
     */
    private User user;

    /**
     * Sets up the mock objects and user for testing
     */
    @BeforeEach
    void setUp() {
        this.user = new User("User", "User@mail.com", "Username", "12345");
        when(userRepository.findByUsername(this.user.getUsername()))
                .thenReturn(Optional.of(this.user));
        when(userRepository.existsByEmail(this.user.getEmail()))
                .thenReturn(true);
        when(userRepository.existsByUsername(this.user.getUsername()))
                .thenReturn(true);
        when(userRepository.save(user)).thenReturn(user);
    }

    /**
     * Tests that the getByUsername method returns the correct user when the username is found
     */
    @Test
    void getByUsernameFound() {
        Optional<User> user1 = userService.getByUsername(this.user.getUsername());
        assertAll(
                () -> assertNotNull(user1),
                () -> assertEquals(user1.get().getUsername(), this.user.getUsername())
        );
    }

    /**
     * Tests that the getByUsername method returns an empty optional when the username is not found
     */
    @Test
    void getByUsernameNotFound() {
        Optional<User> user1 = userService.getByUsername("doesNotExist");
        assertEquals(user1, Optional.empty());
    }

    /**
     * Tests that the existsByUsername method returns true when the username exists
     */
    @Test
    void existsByUsernameTrue() {
        assertTrue(userService.existsByUsername(this.user.getUsername()));
    }

    /**
     * Tests that the existsByUsername method returns false when the username does not exist
     */
    @Test
    void existsByUsernameFalse() {
        assertFalse(userService.existsByUsername("doesNotExist"));
    }

    /**
     * Tests that the existsByEmail method returns true when the email exists
     */
    @Test
    void existsByEmailTrue() {
        assertTrue(userService.existsByEmail(this.user.getEmail()));
    }

    /**
     * Tests that the existsByEmail method returns false when the email does not exist
     */
    @Test
    void existsByEmailFalse() {
        assertFalse(userService.existsByEmail("doesNotExist"));
    }

    /**
     * Tests that the getUserDetails method returns the correct user details
     */
    @Test
    void getUserDetails() {
        assertNotNull(userService.getUserDetails(this.user.getUsername()));
    }

    /**
     * Tests that the save method saves the user to the database
     */
    @Test
    void save() {
        userService.save(this.user);
        verify(userRepository, times(1)).save(this.user);
    }

}