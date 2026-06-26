package models.repositories.jpa;

import models.User;
import models.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserRepositoryJpaAdapter implements UserRepository {
    private final UserJpaRepository delegate;

    public UserRepositoryJpaAdapter(UserJpaRepository delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<User> findAll() {
        return delegate.findAll();
    }

    @Override
    public Optional<User> findById(String id) {
        return delegate.findById(id);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return delegate.findByLogin(login);
    }

    @Override
    public User save(User user) {
        if (user.getId() == null || user.getId().isBlank()) {
            user.setId(UUID.randomUUID().toString());
        }

        return delegate.save(user);
    }

    @Override
    public void deleteById(String id) {
        delegate.deleteById(id);
    }
}
