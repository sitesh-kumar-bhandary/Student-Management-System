package com.siteshkumar.student_management_system.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;

import jakarta.mail.internet.MimeMessage;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
    
    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private TemplateEngine templateEngine;

    @InjectMocks
    private EmailService emailService;

    @Test
    void sendStudentCreatedEmailTest(){

        // Arrange
        MimeMessage mimeMessage = mock(MimeMessage.class);

        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        when(templateEngine.process(anyString(), any())).thenReturn("<html>Email</html>");

        // Act
        emailService.sendStudentCreatedEmail("student@test.com", "Sitesh", "password123");

        // Assert
        verify(javaMailSender, times(1)). createMimeMessage();
        verify(templateEngine, times(1)).process(eq("StudentCreated"), any());
        verify(javaMailSender, times(1)).send(mimeMessage);
    }

    @Test
    void sendPasswordUpdateEmailTest(){

        // Arrange
        MimeMessage mimeMessage = mock(MimeMessage.class);

        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        when(templateEngine.process(anyString(), any())).thenReturn("<html>Email</html>");

        // Act
        emailService.sendPasswordUpdateEmail("student@test.com", "Sitesh");

        // Assert
        verify(javaMailSender, times(1)).createMimeMessage();
        verify(templateEngine, times(1)).process(eq("password-updated"), any());
        verify(javaMailSender, times(1)).send(mimeMessage);
    }
}
