package com.cardavid7.ms.security.model.services;

import java.util.List;
import java.util.Optional;

import com.cardavid7.ms.security.model.entities.User;

public interface UserService {

	List<User> findAll();

	Optional<User> findById(long id);

	Optional<User> findByUsername(String username);

	User save(User user);

	User update(User user, long id);

	void delete(long id);

}
