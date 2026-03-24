package com.cardavid7.ms.security.model.services.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cardavid7.ms.security.model.entities.Role;
import com.cardavid7.ms.security.model.entities.User;
import com.cardavid7.ms.security.model.repositories.UserRepository;
import com.cardavid7.ms.security.model.services.RoleService;
import com.cardavid7.ms.security.model.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return Optional.of(userRepository.findByUsername(username));
    }

    @Override
    @Transactional
    public User save(User user) {
        user.setRoles(getRoles(user));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User update(User user, long id) {
        Optional<User> userOptional = findById(id);
        if (userOptional.isEmpty()) {
            return null;
        }
        User userToUpdate = userOptional.get();
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setEnabled(user.isEnabled());
        userToUpdate.setRoles(getRoles(user));
        return userRepository.save(userToUpdate);
    }

    @Override
    @Transactional
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    private Set<Role> getRoles(User user) {

        Set<Role> roles = new LinkedHashSet<>();
        if (user.isAdmin()) {
            Optional<Role> adminRoleOptional = roleService.findByName("ROLE_ADMIN");
            if (adminRoleOptional.isPresent()) {
                roles.add(adminRoleOptional.get());
            }
        }

        Optional<Role> userRoleOptional = roleService.findByName("ROLE_USER");
        if (userRoleOptional.isPresent()) {
            roles.add(userRoleOptional.get());
        }

        return roles;
    }
}
