package com.cardavid7.ms.security.model.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cardavid7.ms.security.model.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);

    User findByUsernameAndPassword(String username, String password);
}
