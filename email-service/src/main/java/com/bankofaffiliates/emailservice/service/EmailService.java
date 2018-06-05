package com.bankofaffiliates.emailservice.service;

import com.bankofaffiliates.common.domain.EmailInput;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    public void sendMail(EmailInput email) throws MessagingException {
        String text = MessageFormat.format(email.getText(), email.getSubject());
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email.getTo());
        helper.setSubject(email.getSubject());
        helper.setText(text);

        mailSender.send(message);
    }
}