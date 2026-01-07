package com.siteshkumar.student_management_system.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.siteshkumar.student_management_system.dto.StudentCreateRequestDto;
import com.siteshkumar.student_management_system.dto.StudentCreateResponseDto;
import com.siteshkumar.student_management_system.dto.StudentResponseDto;
import com.siteshkumar.student_management_system.dto.StudentUpdateRequestDto;
import com.siteshkumar.student_management_system.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/students")
public class StudentController {
    
    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentCreateResponseDto> createStudent(@Valid @RequestBody StudentCreateRequestDto dto){
        StudentCreateResponseDto createdStudent = studentService.createStudent(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<StudentResponseDto> updateStudent(@PathVariable Long studentId, @Valid @RequestBody StudentUpdateRequestDto dto){
        StudentResponseDto updatedStudent = studentService.updateStudent(studentId, dto);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId){
        studentService.deleteStudent(studentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentResponseDto> getStudentById(@PathVariable Long studentId){
        StudentResponseDto student = studentService.getStudentById(studentId);
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public ResponseEntity<Page<StudentResponseDto>> getAllStudents(Pageable pageable){
        Page<StudentResponseDto> students = studentService.getAllStudents(pageable);
        return ResponseEntity.ok(students);
    }

    // Implementing searching funtionality
    @GetMapping("/search")
    public ResponseEntity<Page<StudentResponseDto>> searchStudents(
        @RequestParam(required = false) String studentName,
        @RequestParam(required = false) String email,
        Pageable pageable
    ){
        Page<StudentResponseDto> students = studentService.searchStudents(studentName, email, pageable);
        return ResponseEntity.ok(students);
    }
}
