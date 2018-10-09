package com.silalahi.valentinus.app.config;

import com.silalahi.valentinus.app.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.List;

public class LoginUser extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = -287835417589260183L;
	private User user;

	public User getUser() {
		return user;
	}

	public LoginUser(User user) {
		super(user.getName(), user.getPassword(), determineRoles(user.getAdmin()));
		this.user = user;
	}

	private static final List<GrantedAuthority> USER_ROLES = AuthorityUtils.createAuthorityList("ROLE_USER");
	private static final List<GrantedAuthority> ADMIN_ROLES = AuthorityUtils.createAuthorityList("ROLE_ADMIN",
			"ROLE_USER");

	private static List<GrantedAuthority> determineRoles(boolean isAdmin) {
		return isAdmin ? ADMIN_ROLES : USER_ROLES;
	}
}
