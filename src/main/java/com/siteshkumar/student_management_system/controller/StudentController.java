package com.siteshkumar.student_management_system.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.siteshkumar.student_management_system.dto.StudentCreateRequestDto;
import com.siteshkumar.student_management_system.dto.StudentCreateResponseDto;
import com.siteshkumar.student_management_system.dto.StudentUpdateRequestDto;
import com.siteshkumar.student_management_system.dto.StudentUpdateResponseDto;
import com.siteshkumar.student_management_system.service.StudentService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/students")
public class StudentController {
    
    private final StudentService studentService;

    @PostMapping("/private/create")
    public ResponseEntity<StudentCreateResponseDto> createStudent(@Valid @RequestBody StudentCreateRequestDto dto){
        StudentCreateResponseDto createdStudent = studentService.createStudent(dto);
        return ResponseEntity.status(201).body(createdStudent);
    }

    public ResponseEntity<StudentUpdateResponseDto> updateStudent(@PathVariable Long studentId, @Valid @RequestBody StudentUpdateRequestDto dto){
        StudentUpdateResponseDto updatedStudent = studentService.updateStudent(studentId, dto);
        return ResponseEntity.status(200).body(updatedStudent);
    }

    public ResponseEntity<> deleteStudent(){

    }

    public ResponseEntity<> getStudentById(){

    }

    public ResponseEntity<> getAllStudents(){

    }
}
