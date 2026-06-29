package com.example.edevicestore.repositories.jpa;

import com.example.edevicestore.models.Role;
import com.example.edevicestore.repositories.RoleRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("jpa")

public class RoleRepositoryJpaAdapter implements RoleRepository {
    private final RoleJpaRepository delegate;

    public RoleRepositoryJpaAdapter(RoleJpaRepository delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<Role> findAll() {
        return delegate.findAll();
    }

    @Override
    public Optional<Role> findById(String id) {
        return delegate.findById(id);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return delegate.findByName(name);
    }

    @Override
    public Role save(Role role) {
        if (role.getId() == null || role.getId().isBlank()) {
            role.setId(UUID.randomUUID().toString());
        }

        return delegate.save(role);
    }
}
