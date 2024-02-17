package com.api.MeteorologicalData.security.service;

import com.api.MeteorologicalData.security.entity.MainUser;
import com.api.MeteorologicalData.security.entity.User;
import com.api.MeteorologicalData.security.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service to handle operations related to the User entity.
 */
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Gets a user by their username.
     *
     * @param username The username of the user to search for.
     * @return An Optional object containing the user if found, or an empty Optional if not.
     */
    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Gets user details for authentication.
     *
     * @param username The username of the user for which details are obtained.
     * @return A UserDetails object that represents the user details for authentication.
     */
    public UserDetails getUserDetails(String username) {
        User user = this.getByUsername(username).get();
        return MainUser.build(user);
    }

    /**
     * Checks if a user with the given username exists.
     *
     * @param username The username to verify.
     * @return true if a user with the username exists, false otherwise.
     */
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Check if a user exists with the provided email.
     *
     * @param email The email to verify.
     * @return true if a user exists with the email, false otherwise.
     */
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Save a new user or update an existing user in the repository.
     *
     * @param user The user to save or update.
     */
    public void save(User user) {
        userRepository.save(user);
    }
}
