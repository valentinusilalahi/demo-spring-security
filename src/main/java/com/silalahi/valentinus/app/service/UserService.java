package com.silalahi.valentinus.app.service;

import java.util.Optional;

import com.silalahi.valentinus.app.entity.User;

public interface UserService {
	Optional<User> findByName(String name);
}
