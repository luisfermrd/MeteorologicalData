package com.api.MeteorologicalData.security.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "\"user\"")
public class User  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    private String username;

    @NotNull
    private String password;

    public User(@NotNull String  name, @NotNull String email, @NotNull String username, @NotNull String password) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
