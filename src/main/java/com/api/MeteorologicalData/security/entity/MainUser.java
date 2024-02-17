package com.api.MeteorologicalData.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * MainUser class is used to represent the user in the system.
 * It contains the user's name, email, username, and password.
 * The MainUser class implements the UserDetails interface, which provides the user's authentication information.
 */
@Data
@AllArgsConstructor
public class MainUser implements UserDetails {
    private String name;
    private String email;
    private String username;
    private String password;

    /**
     * The build method creates a new MainUser instance based on the given User instance.
     *
     * @param user the User instance
     * @return the MainUser instance
     */
    public static MainUser build(User user) {
        return new MainUser(user.getName(), user.getEmail(), user.getUsername(), user.getPassword());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
