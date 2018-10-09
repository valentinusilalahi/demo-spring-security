package com.silalahi.valentinus.app.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	public SimpleAuthenticationSuccessHandler() {
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, 
			HttpServletResponse response,
			Authentication exception) throws IOException, ServletException {
		if (response.isCommitted()) {
			log.info("Response has already been commited.");
			return;
		}
		response.setStatus(HttpStatus.OK.value());
		clearAuthenticationAttributes(request);
	}

	private void clearAuthenticationAttributes(HttpServletRequest request) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

	}

}
