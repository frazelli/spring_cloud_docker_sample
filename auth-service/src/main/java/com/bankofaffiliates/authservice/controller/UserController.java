package com.bankofaffiliates.authservice.controller;

import com.bankofaffiliates.authservice.domain.User;
import com.bankofaffiliates.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private AuthService authService;

	@GetMapping("/current")
	public Principal getUser(Principal principal) {
		return principal;
	}

	@PreAuthorize("#oauth2.hasScope('server')")
	@PostMapping("/create")
	public void createUser(@Valid @RequestBody User user) {
		authService.create(user);
	}

//	@PreAuthorize("#oauth2.hasScope('server')")
//	@PreAuthorize("hasRole('ROLE_CLIENT')")
	@GetMapping("/testServer")
	public String testServerScope() {
		return "I'm in Server Scope!";
	}

//	@PreAuthorize("#oauth2.hasScope('ui')")
//	@PreAuthorize("hasRole('ROLE_CLIENT')")
	@GetMapping("/testUi")
	public String testUiScope() {
		return "I'm in Ui Scope!";
	}
}
