package com.bankofaffiliates.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "auth-service")
public interface AuthServiceClient {

	@GetMapping("/users/testServer")
	String testServerScope();

	@GetMapping("/users/testUi")
	String testUiScope();

}
