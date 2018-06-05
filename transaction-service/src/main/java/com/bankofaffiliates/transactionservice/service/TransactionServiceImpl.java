package com.bankofaffiliates.transactionservice.service;

import com.bankofaffiliates.transactionservice.client.UserServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private UserServiceClient userClient;

	@Override
	public String testServerScope() {
		return userClient.testUserService();
	}
}
