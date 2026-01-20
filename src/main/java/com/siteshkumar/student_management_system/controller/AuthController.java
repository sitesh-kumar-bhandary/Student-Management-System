package com.siteshkumar.student_management_system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.siteshkumar.student_management_system.dto.ChangePasswordRequestDto;
import com.siteshkumar.student_management_system.dto.LoginRequestDto;
import com.siteshkumar.student_management_system.dto.LoginResponseDto;
import com.siteshkumar.student_management_system.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request){
        LoginResponseDto loggedInUser = authService.login(request);
        return ResponseEntity.ok(loggedInUser);
    }   

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRequestDto request){
        authService.changePassword(request);
        return ResponseEntity.noContent().build();
    }
}
