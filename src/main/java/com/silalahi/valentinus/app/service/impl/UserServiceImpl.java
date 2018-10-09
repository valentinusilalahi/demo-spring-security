package com.silalahi.valentinus.app.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.silalahi.valentinus.app.entity.User;
import com.silalahi.valentinus.app.repository.UserRepository;
import com.silalahi.valentinus.app.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Optional<User> findByName(String name) {
		Objects.requireNonNull(name, "name must be not null");
		return userRepository.findFirstByName(name);
	}

}
