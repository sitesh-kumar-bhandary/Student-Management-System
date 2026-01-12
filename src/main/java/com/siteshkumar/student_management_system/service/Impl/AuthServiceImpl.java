package com.siteshkumar.student_management_system.service.Impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.siteshkumar.student_management_system.dto.LoginRequestDto;
import com.siteshkumar.student_management_system.dto.LoginResponseDto;
import com.siteshkumar.student_management_system.dto.SignupRequestDto;
import com.siteshkumar.student_management_system.dto.SignupResponseDto;
import com.siteshkumar.student_management_system.entity.Role;
import com.siteshkumar.student_management_system.entity.StudentEntity;
import com.siteshkumar.student_management_system.entity.UserEntity;
import com.siteshkumar.student_management_system.exception.DuplicateResourceException;
import com.siteshkumar.student_management_system.repository.UserRepository;
import com.siteshkumar.student_management_system.security.AuthUtils;
import com.siteshkumar.student_management_system.security.CustomUserDetails;
import com.siteshkumar.student_management_system.service.AuthService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AuthUtils authUtils;

    @Transactional
    @Override
    public SignupResponseDto signup(SignupRequestDto request) {
        if(userRepository.existsByEmail(request.getEmail()))
            throw new DuplicateResourceException("Email already exists");

        StudentEntity student = new StudentEntity();
        student.setStudentName("ABC");
        student.setEmail(request.getEmail());

        UserEntity user = new UserEntity();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.STUDENT);
        user.setStudent(student);        

        UserEntity createdUser = userRepository.save(user);

        return new SignupResponseDto(
            createdUser.getId(),
            createdUser.getEmail(),
            createdUser.getRole()
        );
    }

    @Override
    public LoginResponseDto login(LoginRequestDto request) {

        // First validate whether that user have account or not
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();

        String token = authUtils.generateAccessToken(user);

        return new LoginResponseDto(
            user.getUsername(),
            token
        );
    }
}
