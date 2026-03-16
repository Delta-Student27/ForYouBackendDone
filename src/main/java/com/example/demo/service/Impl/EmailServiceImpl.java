package com.example.demo.service.Impl;

import com.example.demo.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    // Spring will automatically inject the mailSender using your application.properties
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("fs8377105@gmail.com"); // Matches your properties
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);
            System.out.println("Email successfully sent to " + to);
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
            // It's good practice to log the error so you know if authentication fails
        }
    }
}