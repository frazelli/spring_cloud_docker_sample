package com.bankofaffiliates.emailservice.controller;

import com.bankofaffiliates.common.domain.EmailInput;
import com.bankofaffiliates.emailservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("sendEmail")
    public ResponseEntity<String> sendEmail(@RequestBody EmailInput email) {
        try {
            emailService.sendMail(email);
        } catch (MessagingException e) {
            System.out.println("------------------");
            System.out.println(e.getCause().getMessage());
            return new ResponseEntity<>(e.getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
