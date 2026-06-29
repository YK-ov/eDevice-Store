package com.example.edevicestore.services;

import com.example.edevicestore.models.User;

import java.util.List;

public interface UserServiceInterface {
    List<User> findAllUsers();
    User findById(String id);
    User findByLogin(String login);
    void deleteUser(String id, String loggedUserId);
    void register(String name, String surname, String email, String login, String password);
}
