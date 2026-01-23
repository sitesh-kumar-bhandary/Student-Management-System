package com.siteshkumar.student_management_system.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import com.siteshkumar.student_management_system.entity.StudentEntity;
import com.siteshkumar.student_management_system.repository.StudentRepository;

@Configuration
@Profile("seed")
public class DataSeederConfig {
    
    @Bean
    CommandLineRunner seedStudent(StudentRepository repo) {
        return args -> {
            for(int i=1;i<=10000;i++){
                StudentEntity s = new StudentEntity();
                s.setStudentName("Student "+i);
                s.setEmail("sitesh"+i+"@test.com");
                repo.save(s);
            }
        };
    }
}
