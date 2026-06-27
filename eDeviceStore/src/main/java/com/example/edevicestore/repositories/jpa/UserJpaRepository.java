package com.example.edevicestore.repositories.jpa;

import com.example.edevicestore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, String> {
    Optional<User> findByLogin(String userLogin);
}
