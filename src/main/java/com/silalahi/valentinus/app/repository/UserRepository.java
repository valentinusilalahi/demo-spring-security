package com.silalahi.valentinus.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.silalahi.valentinus.app.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	Optional<User> findFirstByName(String name);
}
