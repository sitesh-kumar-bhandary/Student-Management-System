package com.siteshkumar.student_management_system.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.siteshkumar.student_management_system.dto.StudentCreateRequestDto;
import com.siteshkumar.student_management_system.dto.StudentCreateResponseDto;
import com.siteshkumar.student_management_system.dto.StudentResponseDto;
import com.siteshkumar.student_management_system.dto.StudentUpdateRequestDto;

public interface StudentService {
    
    public StudentCreateResponseDto createStudent(StudentCreateRequestDto dto);
    public StudentResponseDto updateStudent(Long studentId, StudentUpdateRequestDto dto);
    public void deleteStudent(Long studentId);
    public StudentResponseDto getStudentById(Long studentId);
    public Page<StudentResponseDto> getAllStudents(Pageable pageable);
}
