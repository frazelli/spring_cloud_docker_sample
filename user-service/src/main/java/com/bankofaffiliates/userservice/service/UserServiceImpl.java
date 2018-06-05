package com.bankofaffiliates.userservice.service;

import com.bankofaffiliates.common.domain.EmailInput;
import com.bankofaffiliates.userservice.client.AuthServiceClient;
import com.bankofaffiliates.common.domain.User;
import com.bankofaffiliates.userservice.client.EmailServiceClient;
import com.bankofaffiliates.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthServiceClient authClient;
    private final EmailServiceClient emailServiceClient;

    @Override
    public User createUser(User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(User.Role.ROLE_CLIENT);
        user.setStatus(User.Status.NEW);
        user = userRepository.save(user);
        return user;
    }

    @Override
    public List<User> listOfUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public void sendEmail(EmailInput email) {
        emailServiceClient.sendEmail(email);
    }

    @Override
    public String testServerScope() {
        return authClient.testServerScope();
    }

    @Override
    public String testUiScope() {
        return authClient.testUiScope();
    }
}
