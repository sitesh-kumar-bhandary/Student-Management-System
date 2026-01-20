package com.siteshkumar.student_management_system.service;

import java.time.LocalDateTime;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Async
    public void sendStudentCreatedEmail(String to, String name, String password) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            Context context = new Context();
            context.setVariable("name", name);
            context.setVariable("email", to);
            context.setVariable("password", password);

            String htmlContent = templateEngine.process("StudentCreated", context);
            helper.setTo(to);
            helper.setFrom("siteshk111@gmail.com");
            helper.setSubject("Your Student Account is Ready.");
            helper.setText(htmlContent, true);

            javaMailSender.send(message);
        }

        catch (Exception ex) {
            log.error("Email sending failed ", ex);
        }
    }

    @Async
    public void sendPasswordUpdateEmail(String email, String name) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            Context context = new Context();
            context.setVariable("name", name);
            context.setVariable("time", LocalDateTime.now());

            String htmlContent = templateEngine.process("password-updated", context);

            helper.setTo(email);
            helper.setSubject("Your Password Was Updated");
            helper.setText(htmlContent, true);

            javaMailSender.send(message);

        } 
        
        catch (Exception ex) {
            log.error("Failed to send password update email to {}", email, ex);
        }
    }

}
