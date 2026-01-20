package com.siteshkumar.student_management_system.service.Impl;

import java.time.LocalDateTime;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.siteshkumar.student_management_system.dto.ChangePasswordRequestDto;
import com.siteshkumar.student_management_system.dto.LoginRequestDto;
import com.siteshkumar.student_management_system.dto.LoginResponseDto;
import com.siteshkumar.student_management_system.entity.PasswordChangeHistoryEntity;
import com.siteshkumar.student_management_system.entity.UserEntity;
import com.siteshkumar.student_management_system.repository.PasswordChangeHistoryRepository;
import com.siteshkumar.student_management_system.security.AuthUtils;
import com.siteshkumar.student_management_system.security.CustomUserDetails;
import com.siteshkumar.student_management_system.service.AuthService;
import com.siteshkumar.student_management_system.service.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final PasswordChangeHistoryRepository passwordChangeHistoryRepository;
    private final AuthUtils authUtils;
    private final EmailService emailService;

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

    @Transactional
    @Override
    public void changePassword(ChangePasswordRequestDto request) {
        CustomUserDetails userDetails = authUtils.getCurrentLoggedInUser();
        UserEntity user = userDetails.getUser();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime windowStart = now.minusDays(7);

        long changeCount = passwordChangeHistoryRepository.countByUserAndChangedAtAfter(user, windowStart);
        if(changeCount >= 3){
            throw new IllegalStateException("Password change limit is reached. Please try again after 7 days.");
        }

        if(! passwordEncoder.matches(request.getOldPassword(), user.getPassword())){
            throw new IllegalArgumentException("Old password is incorrect");
        }

        String newPassword = passwordEncoder.encode(request.getNewPassword());
        user.setPassword(newPassword);

        PasswordChangeHistoryEntity history = new PasswordChangeHistoryEntity();
        history.setUser(user);
        history.setChangedAt(now);

        passwordChangeHistoryRepository.save(history);

        // Email notification
        emailService.sendPasswordUpdateEmail(
            user.getEmail(),
            user.getStudent() != null ? user.getStudent().getStudentName() : user.getEmail()
        );
    }
}
