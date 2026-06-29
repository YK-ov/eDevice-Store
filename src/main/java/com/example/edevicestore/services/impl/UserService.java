package com.example.edevicestore.services.impl;

import com.example.edevicestore.models.Order;
import com.example.edevicestore.models.Role;
import com.example.edevicestore.models.User;
import com.example.edevicestore.repositories.BasketItemRepository;
import com.example.edevicestore.repositories.OrderRepository;
import com.example.edevicestore.repositories.RoleRepository;
import com.example.edevicestore.repositories.UserRepository;
import com.example.edevicestore.services.UserServiceInterface;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional

public class UserService implements UserServiceInterface {
    private final UserRepository userRepository;
    private final BasketItemRepository basketItemRepository;
    private final OrderRepository orderRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BasketItemRepository basketItemRepository, OrderRepository orderRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.basketItemRepository = basketItemRepository;
        this.orderRepository = orderRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(String id) {
        Optional<User> foundUser = userRepository.findById(id);

        if (foundUser.isEmpty()){
            throw new IllegalArgumentException("Uzytkownika o id " + id + " nie znaleziono w systemie");
        }

        return foundUser.get();
    }

    @Override
    @Transactional(readOnly = true)
    public User findByLogin(String login) {
        Optional<User> foundUser = userRepository.findByLogin(login);

        if (foundUser.isEmpty()){
            throw new IllegalArgumentException("Uzytkownika o loginie " + login + " nie znaleziono w systemie");
        }

        return foundUser.get();
    }

    @Override
    @Transactional
    public void deleteUser(String id, String loggedUserId) {
        Optional<User> loggedUser = userRepository.findById(loggedUserId);

        if (loggedUser.isEmpty()){
            throw new IllegalArgumentException("Nie jestes zalogowany, nie znaleziono uzytkownika o id " + loggedUserId);
        }

        boolean isAdmin = loggedUser.get().getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        if (!isAdmin){
            throw new IllegalArgumentException("Tylko administrator moze usuwac konta innych uzytkownikow");
        }

        Optional<User> userToDelete = userRepository.findById(id);

        if (userToDelete.isEmpty()){
            throw new IllegalArgumentException("Uzytkownika o id " + id + " nie znaleziono w systemie, nie mozna go usunac");
        }

        isAdmin = userToDelete.get().getRoles().stream()
                        .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        if (isAdmin){
            throw new IllegalArgumentException("Nie mozna usunac kotna administratora");
        }

        basketItemRepository.deleteAllByUserId(id);

        List<Order> userOrders = orderRepository.findByUserId(id);

        orderRepository.deleteAll(userOrders);

        userRepository.deleteById(id);
    }

    public void register(String name, String surname, String email, String login, String password) {
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Login nie może być pusty");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Hasło nie może być puste");
        }
        if (userRepository.findByLogin(login).isPresent()) {
            throw new IllegalArgumentException("Użytkownik już istnieje");
        }
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("Brak ROLE_USER"));
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .name(name)
                .surname(surname)
                .email(email)
                .login(login)
                .passwordHash(passwordEncoder.encode(password))
                .roles(Set.of(userRole))
                .build();
        userRepository.save(user);
    }

}
