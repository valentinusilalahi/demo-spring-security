package com.silalahi.valentinus.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "prelogin")
@Slf4j
public class PreLoginController {
	public String preLogin(HttpServletRequest request) {
		log.info(request.toString());
		DefaultCsrfToken token = (DefaultCsrfToken) request.getAttribute("_csrf");
		if (token == null) {
			throw new RuntimeException("Could not get a token");
		}
		return token.getToken();
	}
}
