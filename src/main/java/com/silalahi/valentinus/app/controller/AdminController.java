package com.silalahi.valentinus.app.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.silalahi.valentinus.app.entity.User;
import com.silalahi.valentinus.app.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping
@Slf4j
public class AdminController {
	private final UserService userService;

	public AdminController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public String greeting(@AuthenticationPrincipal(expression = "user") User user) {
		log.info("access user : {}", user.toString());
		return "hello admin " + user.getName();
	}

	@GetMapping(path = "{name}")
	public String greeting(@AuthenticationPrincipal(expression = "user") User user,
			@PathVariable(name = "name") String name) {
		log.info("access user : {}", user.toString());
		return userService.findByName(name).map(u -> "hello " + u.getName()).orElse("unknown user");
	}

	@GetMapping(path = "echo/{message}")
	public String getEcho(@PathVariable(name = "message") String message) {
		return message.toUpperCase();
	}

	@PostMapping(path = "echo", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String postEcho(@RequestBody Map<String, String> message) {
		return message.toString();
	}
}
