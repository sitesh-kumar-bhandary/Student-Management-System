package com.siteshkumar.student_management_system.service;

import com.siteshkumar.student_management_system.dto.StudentCreateRequestDto;
import com.siteshkumar.student_management_system.dto.StudentCreateResponseDto;
import com.siteshkumar.student_management_system.dto.StudentUpdateRequestDto;
import com.siteshkumar.student_management_system.dto.StudentUpdateResponseDto;

public interface StudentService {
    
    public StudentCreateResponseDto createStudent(StudentCreateRequestDto dto);
    public StudentUpdateResponseDto updateStudent(Long studentId, StudentUpdateRequestDto dto);

}
