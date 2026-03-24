package com.cardavid7.ms.security.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardavid7.ms.security.model.entities.User;
import com.cardavid7.ms.security.model.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<List<User>> findAll() {
		return ResponseEntity.ok(userService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		Optional<User> userOptional = userService.findById(id);
		if (userOptional.isPresent()) {
			return ResponseEntity.ok(userOptional.get());
		}
		return ResponseEntity.noContent().build();
	}

	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody User user) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id,
			@Valid @RequestBody User user) {
		Optional<User> userOptional = userService.findById(id);
		if (userOptional.isPresent()) {
			return ResponseEntity.ok(userService.update(user, id));
		}
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<User> userOptional = userService.findById(id);
		if (userOptional.isPresent()) {
			userService.delete(userOptional.get().getId());
			return ResponseEntity.ok(String.format("User with id %s was deleted", id));
		}
		return ResponseEntity.noContent().build();
	}
}
