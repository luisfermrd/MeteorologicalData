package com.api.MeteorologicalData.security.repository;

import com.api.MeteorologicalData.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * This interface provides methods for interacting with the database and retrieving user information.
 * It extends the JpaRepository interface, which provides basic CRUD operations for the User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * This method finds a user by their username.
     *
     * @param username the username of the user
     * @return an Optional containing the user if found, or an empty Optional if not found
     */
    Optional<User> findByUsername(String username);

    /**
     * This method checks if a user exists by their username.
     *
     * @param username the username of the user
     * @return true if a user with the given username exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * This method checks if a user exists by their email.
     *
     * @param email the email of the user
     * @return true if a user with the given email exists, false otherwise
     */
    boolean existsByEmail(String email);

}
