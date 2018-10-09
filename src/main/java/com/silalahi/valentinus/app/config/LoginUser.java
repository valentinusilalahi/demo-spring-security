package com.silalahi.valentinus.app.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

public class LoginUser extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 1L;

	private com.silalahi.valentinus.app.entity.User user;

	public com.silalahi.valentinus.app.entity.User getUser() {
		return user;
	}

	public LoginUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		// TODO Auto-generated constructor stub
	}

	public LoginUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		// TODO Auto-generated constructor stub
	}

	private static final List<GrantedAuthority> USER_ROLES = AuthorityUtils.createAuthorityList("ROLE_USER");
	private static final List<GrantedAuthority> ADMIN_ROLES = AuthorityUtils.createAuthorityList("ROLE_ADMIN",
			"ROLE_USER");

	private static List<GrantedAuthority> determineRoles(boolean isAdmin) {
		return isAdmin ? ADMIN_ROLES : USER_ROLES;
	}

}
