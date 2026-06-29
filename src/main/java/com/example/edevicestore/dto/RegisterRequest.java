package com.example.edevicestore.dto;

public record RegisterRequest(
        String name,
        String surname,
        String login,
        String email,
        String password
) { }

