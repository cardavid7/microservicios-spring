package com.cardavid7.ms.security.model.services;

import java.util.Optional;

import com.cardavid7.ms.security.model.entities.Role;

public interface RoleService {

    Optional<Role> findByName(String name);

}
