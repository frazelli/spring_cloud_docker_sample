package com.bankofaffiliates.authservice.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

import static com.bankofaffiliates.common.domain.User.Role.ROLE_ADMIN;
import static com.bankofaffiliates.common.domain.User.Role.ROLE_CLIENT;
import static com.bankofaffiliates.common.domain.User.Status.*;

@Entity
@Table(name = "account")
public class User implements UserDetails {

	@Column(name = "id")
	private Integer id;

	@Id
	@Column(name = "email")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "role")
	private com.bankofaffiliates.common.domain.User.Role role;

	@Column(name = "status")
	private com.bankofaffiliates.common.domain.User.Status status;

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public List<GrantedAuthority> getAuthorities() {
		if (role == ROLE_CLIENT) {
			return AuthorityUtils.createAuthorityList("ROLE_CLIENT");
		} else if (role == ROLE_ADMIN) {
			return AuthorityUtils.createAuthorityList("ROLE_ADMIN");
		} else {
			return AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS");
		}
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		if (status == BLOCKED) {
			return false;
		} else if (status == CONFIRMED || status == NEW){
			return true;
		} else {
			return false;
		}
	}
}
