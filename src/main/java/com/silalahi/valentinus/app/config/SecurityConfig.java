package com.silalahi.valentinus.app.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	public void configureGlobal(AuthenticationManagerBuilder auth,
			@Qualifier("simpleUserDetailsService") UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) throws Exception {
		auth
		.eraseCredentials(true)
		.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
		http.authorizeRequests()
			.mvcMatchers("/prelogin", "/hello/**").permitAll()
			.mvcMatchers("/user/**").hasRole("USER")
			.mvcMatchers("/admin/**").hasRole("ADMIN")
			.anyRequest().authenticated()
			.and()
			.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
			.accessDeniedHandler(accessDeniedHandler())
			.and()
			.formLogin().loginProcessingUrl("/login")
			.permitAll()
			.usernameParameter("email").passwordParameter("pass")
			.successHandler(authenticationSuccessHandler())
			.failureHandler(authenticationFailureHandler())
			.and()
			.logout().logoutUrl("/logout")
			.invalidateHttpSession(true).deleteCookies("JSESSIONID")
			.logoutSuccessHandler(logoutSuccessHandler())
			.and()
			.csrf().csrfTokenRepository(new CookieCsrfTokenRepository())
			.and()
			.sessionManagement()
			.maximumSessions(1)
			.maxSessionsPreventsLogin(false);
	}

	private AuthenticationEntryPoint authenticationEntryPoint() {
		return new SimpleAuthenticationEntryPoint();
	}

	private AccessDeniedHandler accessDeniedHandler() {
		return new SimpleAccessDeniedHandler();
	}

	private AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new SimpleAuthenticationSuccessHandler();
	}

	private AuthenticationFailureHandler authenticationFailureHandler() {
		return new SimpleAuthenticationFailureHandler();
	}

	private LogoutSuccessHandler logoutSuccessHandler() {
		return new HttpStatusReturningLogoutSuccessHandler();
	}

}
