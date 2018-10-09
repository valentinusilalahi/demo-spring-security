package com.silalahi.valentinus.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "hallo")
@Slf4j
public class HelloController {

	@GetMapping
	public String greeting() {
		return "Hallo Dunia";
	}

	@GetMapping(path = "{message}")
	public String greeting(@PathVariable(name = "message") String message) {
		return "hallo" + message;
	}

	@PostMapping
	public String postGreeting(@RequestParam(name = "message") String message) {
		return "hallo" + message;
	}
}
