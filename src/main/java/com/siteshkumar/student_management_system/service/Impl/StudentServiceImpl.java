package com.siteshkumar.student_management_system.service.Impl;

import org.springframework.stereotype.Service;
import com.siteshkumar.student_management_system.dto.StudentCreateRequestDto;
import com.siteshkumar.student_management_system.dto.StudentCreateResponseDto;
import com.siteshkumar.student_management_system.dto.StudentUpdateRequestDto;
import com.siteshkumar.student_management_system.dto.StudentUpdateResponseDto;
import com.siteshkumar.student_management_system.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService{

    @Override
    public StudentCreateResponseDto createStudent(StudentCreateRequestDto dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createStudent'");
    }

    @Override
    public StudentUpdateResponseDto updateStudent(Long studentId, StudentUpdateRequestDto dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateStudent'");
    }
    
}
