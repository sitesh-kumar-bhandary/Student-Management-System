package com.siteshkumar.student_management_system.service;

import com.siteshkumar.student_management_system.dto.LoginRequestDto;
import com.siteshkumar.student_management_system.dto.LoginResponseDto;
import com.siteshkumar.student_management_system.dto.SignupRequestDto;
import com.siteshkumar.student_management_system.dto.SignupResponseDto;

public interface AuthService {
    SignupResponseDto signup(SignupRequestDto request);
    LoginResponseDto login(LoginRequestDto request);
}
