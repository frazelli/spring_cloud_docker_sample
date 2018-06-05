package com.bankofaffiliates.userservice.controller;

import com.bankofaffiliates.common.domain.EmailInput;
import com.bankofaffiliates.common.utils.BeanCopy;
import com.bankofaffiliates.common.domain.User;
import com.bankofaffiliates.userservice.repository.UserRepository;
import com.bankofaffiliates.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.lang.Math.abs;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("current")
    public Principal getUser(Principal principal) {
        return principal;
    }

    @PostMapping("create")
    public User create(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || #id == authentication.principal")
    @PutMapping("edit/{id}")
    public ResponseEntity<User> edit(@PathVariable long id, @RequestBody User input) throws InvocationTargetException, IllegalAccessException  {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            BeanCopy.nullAwareBeanCopy(user.get(), user);
            userRepository.save(user.get());
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || #id == authentication.principal")
    @PutMapping("changeStatus/{id}")
    public ResponseEntity<User> changeStatus(@PathVariable long id, @RequestBody boolean statusInput) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            User.Status newStatus;
            if (statusInput) {
                newStatus = User.Status.BLOCKED;
            } else {
                newStatus = User.Status.CONFIRMED;
            }
            user.get().setStatus(newStatus);
            userRepository.save(user.get());

            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("recoveryPassword/{email}")
    public ResponseEntity<String> recoveryPassword(@PathVariable String email) {
        User user = userRepository.findByEmail(email);

        SecureRandom secureRandom = new SecureRandom();
        if (user != null) {
            String recoveryLink = String.valueOf(abs(secureRandom.nextLong()));
            user.setEmailRecoveryToken(recoveryLink);
            user.setEmailRecoveryTokenDateExpired(new Date(System.currentTimeMillis() + 1000 * 60 * 60));

            EmailInput emailInput = new EmailInput(user.getEmail(), email, "Recovery link - " + recoveryLink, "Forgot password?");

            userRepository.save(user);
            new Thread(() -> userService.sendEmail(emailInput)).start();

            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("confirmRecoveryPassword")
    public ResponseEntity<String> confirmRecoveryPassword(String recoveryToken, String newPassword)  {
        User user = userRepository.findByEmailRecoveryToken(recoveryToken);

        if (user != null) {
            if (System.currentTimeMillis() - user.getEmailRecoveryTokenDateExpired().getTime() > 1000 * 60 * 60) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                user.setPassword(bCryptPasswordEncoder.encode(newPassword));
                userRepository.save(user);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN, ROLE_CLIENT')")
    @GetMapping("list")
    public List<User> listOfUsers() {
        return userService.listOfUsers();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN, ROLE_CLIENT')")
    @GetMapping("testServer")
    public String testServerScope() {
        return userService.testServerScope();
    }

    @GetMapping("testUi")
    public String testUiScope() {
        return userService.testUiScope();
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @GetMapping("testUserService")
    public String testUserService() {
        return "URAAAAAAAAAAA";
    }

}
