package com.api.MeteorologicalData.security.entity;

import com.api.MeteorologicalData.entity.Audit;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * This class represents a user in the system.
 * It contains information such as name, email, username, and password.
 * It also contains a list of audits performed by the user.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "\"user\"")
public class User implements Serializable {

    /**
     * The unique identifier of the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the user.
     */
    @NotNull
    private String name;

    /**
     * The email of the user.
     */
    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    /**
     * The username of the user.
     */
    @NotNull
    @Column(unique = true)
    private String username;

    /**
     * The password of the user.
     */
    @NotNull
    private String password;

    /**
     * A list of audits performed by the user.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Audit> audits;

    /**
     * Creates a new user with the given details.
     *
     * @param name     the name of the user
     * @param email    the email of the user
     * @param username the username of the user
     * @param password the password of the user
     */
    public User(@NotNull String name, @NotNull String email, @NotNull String username, @NotNull String password) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
