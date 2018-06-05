package com.bankofaffiliates.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@SpringBootApplication
@EntityScan("com.bankofaffiliates.common.domain")
@EnableResourceServer
public class UserServiceApplication extends ResourceServerConfigurerAdapter {
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
}
