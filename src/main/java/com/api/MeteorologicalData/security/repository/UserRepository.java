package com.api.MeteorologicalData.security.repository;

import com.api.MeteorologicalData.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
