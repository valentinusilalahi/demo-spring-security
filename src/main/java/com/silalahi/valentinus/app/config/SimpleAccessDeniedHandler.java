package com.silalahi.valentinus.app.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.CsrfException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleAccessDeniedHandler implements AccessDeniedHandler {

	public SimpleAccessDeniedHandler() {
	}

	@Override
	public void handle(HttpServletRequest request, 
			HttpServletResponse response, 
			AccessDeniedException exception) throws IOException, ServletException {

		if (response.isCommitted()) {
			log.info("Response has already been commited.");
			return;
		}

		dump(exception);
		response.sendError(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase());

	}

	private void dump(AccessDeniedException e) {
		if (e instanceof AuthorizationServiceException) {
			log.debug("AuthorizationServiceException : {}", e.getMessage());
		} else if (e instanceof CsrfException) {
			log.debug("org.springframework.security.web.csrf.CsrfException : {}", e.getMessage());
		} else if (e instanceof org.springframework.security.web.server.csrf.CsrfException) {
			log.debug("org.springframework.security.web.server.csrf.CsrfException : {}", e.getMessage());
		} else {
			log.debug("AccessDeniedException : {}", e.getMessage());
		}
	}

}
