package com.cardavid7.ms.security.model.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardavid7.ms.security.model.entities.Role;
import com.cardavid7.ms.security.model.repositories.RoleRepository;
import com.cardavid7.ms.security.model.services.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

}
