package com.siteshkumar.student_management_system.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.siteshkumar.student_management_system.dto.StudentCreateRequestDto;
import com.siteshkumar.student_management_system.dto.StudentCreateResponseDto;
import com.siteshkumar.student_management_system.dto.StudentDeleteRequestDto;
import com.siteshkumar.student_management_system.dto.StudentUpdateRequestDto;
import com.siteshkumar.student_management_system.dto.StudentUpdateResponseDto;
import com.siteshkumar.student_management_system.service.StudentService;
import jakarta.validation.Valid;
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

    @PutMapping("/private/update/{studentId}")
    public ResponseEntity<StudentUpdateResponseDto> updateStudent(@PathVariable Long studentId, @Valid @RequestBody StudentUpdateRequestDto dto){
        StudentUpdateResponseDto updatedStudent = studentService.updateStudent(studentId, dto);
        return ResponseEntity.status(200).body(updatedStudent);
    }

    @DeleteMapping("/private/delete/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId){
        studentService.deleteStudent(studentId);
        return ResponseEntity.noContent().build();
    }

    // @GetMapping("/private/students")
    // public ResponseEntity<> getStudentById(){

    // }

    // @GetMapping("/private/students/{studentId}")
    // public ResponseEntity<> getAllStudents(){
        
    // }
}
