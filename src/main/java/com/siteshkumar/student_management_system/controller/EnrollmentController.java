package com.siteshkumar.student_management_system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.siteshkumar.student_management_system.service.EnrollmentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    
    @PostMapping
    public ResponseEntity<Void> enrollStudent(@RequestParam Long studentId, @RequestParam Long courseId){
        enrollmentService.enrollStudent(studentId, courseId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removeEnrollment(@RequestParam Long studentId, @RequestParam Long courseId){
        enrollmentService.removeEnrollment(studentId, courseId);
        return ResponseEntity.noContent().build();
    }
}
