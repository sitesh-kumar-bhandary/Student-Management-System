package com.siteshkumar.student_management_system.service;

import com.siteshkumar.student_management_system.dto.ChangePasswordRequestDto;
import com.siteshkumar.student_management_system.dto.LoginRequestDto;
import com.siteshkumar.student_management_system.dto.LoginResponseDto;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto request);
    void changePassword(ChangePasswordRequestDto request);
}
