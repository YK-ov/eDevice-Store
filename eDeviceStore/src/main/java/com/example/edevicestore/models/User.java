package com.example.edevicestore.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "passwordHash")
@Entity
@Table(name = "users")

public class User {
    @Id
    @Column(nullable = false, unique = true)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "email", nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public User copy(){
        return User.builder()
                .id(id)
                .name(name)
                .surname(surname)
                .login(login)
                .passwordHash(passwordHash)
                .email(email)
                .role(role)
                .build();
    }

}
