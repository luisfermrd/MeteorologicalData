package com.api.MeteorologicalData.security.service;

import com.api.MeteorologicalData.security.entity.MainUser;
import com.api.MeteorologicalData.security.entity.User;
import com.api.MeteorologicalData.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Optional<User> getByUsername(String username){
        return  userRepository.findByUsername(username);
    }

    public UserDetails getUserDetails(String username){
        User user = this.getByUsername(username).get();
        return MainUser.build(user);
    }

    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }
    public void save(User user){
        userRepository.save(user);
    }
}
