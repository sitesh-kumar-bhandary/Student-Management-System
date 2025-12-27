package com.siteshkumar.student_management_system.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import com.siteshkumar.student_management_system.entity.StudentEntity;
import com.siteshkumar.student_management_system.repository.StudentRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class StudentRepositoryRunner implements CommandLineRunner{

    private final StudentRepository studentRepository;

    @Override
    public void run(String... args){
        Pageable pageable = PageRequest.of(0, 2);

        Page<StudentEntity> page = studentRepository.findByStudentName("Rahul", pageable);

        System.out.println("--------------Paginated Students---------------");
        page.getContent().forEach(
            student -> System.out.println(student.getStudentName())
        );

        System.out.println("Total records : "+ page.getTotalElements());
        System.out.println("Total pages : "+ page.getTotalPages());
    }
}