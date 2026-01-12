package com.siteshkumar.student_management_system.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.siteshkumar.student_management_system.entity.Role;
import com.siteshkumar.student_management_system.entity.UserEntity;
import com.siteshkumar.student_management_system.repository.UserRepository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AdminBootstrapRunner implements CommandLineRunner{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        String email = "siteshk111@gmail.com";

        if( ! userRepository.existsByEmail(email)){
            UserEntity admin = new UserEntity();
            admin.setEmail(email);
            admin.setPassword(passwordEncoder.encode("12341234"));
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);
            System.out.println("Default admin created successfully");
        }

        else
            System.out.println("Admin is already exists");
    }
}
