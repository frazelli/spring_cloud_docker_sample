package com.bankofaffiliates.authservice.service.security;

import com.bankofaffiliates.authservice.domain.User;
import com.bankofaffiliates.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = repository.findByUsername(email);

		if (user == null) {
			throw new UsernameNotFoundException(email);
		}

		return user;
	}
}
