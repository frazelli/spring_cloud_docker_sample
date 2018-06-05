package com.bankofaffiliates.userservice.service;

import com.bankofaffiliates.common.domain.EmailInput;
import com.bankofaffiliates.common.domain.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    User createUser(User user);

    List<User> listOfUsers();

    String testServerScope();

    String testUiScope();

    void sendEmail(EmailInput email);
}
