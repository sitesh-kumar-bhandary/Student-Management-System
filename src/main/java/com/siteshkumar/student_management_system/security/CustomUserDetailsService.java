package com.siteshkumar.student_management_system.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.siteshkumar.student_management_system.entity.UserEntity;
import com.siteshkumar.student_management_system.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        log.info("Loading user details for email: {}", email);

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("User not found while loading user details for email: {}", email);
                    return new UsernameNotFoundException(
                            "Invalid username or password");
                });

        log.info("User details loaded successfully for email: {}", email);

        return new CustomUserDetails(user);
    }
}
