package com.siteshkumar.student_management_system.service.Impl;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import com.siteshkumar.student_management_system.dto.StudentCreateRequestDto;
import com.siteshkumar.student_management_system.dto.StudentCreateResponseDto;
import com.siteshkumar.student_management_system.dto.StudentUpdateRequestDto;
import com.siteshkumar.student_management_system.dto.StudentUpdateResponseDto;
import com.siteshkumar.student_management_system.entity.StudentEntity;
import com.siteshkumar.student_management_system.exception.StudentNotFoundException;
import com.siteshkumar.student_management_system.repository.StudentRepository;
import com.siteshkumar.student_management_system.service.StudentService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;

    @Override
    public StudentCreateResponseDto createStudent(StudentCreateRequestDto dto) {
        if(studentRepository.existsByEmailIgnoreCase(dto.getEmail()))
            throw new IllegalArgumentException("Student with this email already exists ");

        StudentEntity student = new StudentEntity();
        student.setStudentName(dto.getStudentName());
        student.setEmail(dto.getEmail());

        StudentEntity savedStudent = studentRepository.save(student);

        return new StudentCreateResponseDto(
            savedStudent.getStudentId(),
            savedStudent.getStudentName(),
            savedStudent.getEmail()
        );
    }

    @Override
    public StudentUpdateResponseDto updateStudent(Long studentId, StudentUpdateRequestDto dto) {
        StudentEntity student = studentRepository.findById(studentId)
                            .orElseThrow(() -> new StudentNotFoundException("Student not found with id: "+studentId));

        if(dto.getVersion() == null || ! student.getVersion().equals(dto.getVersion()))
            throw new OptimisticLockingFailureException("Student was already updated by another user. Please refresh!");

        if(dto.getStudentName() != null)
            student.setStudentName(dto.getStudentName());

        if(dto.getEmail() != null && ! dto.getEmail().equalsIgnoreCase(student.getEmail())){
            if(studentRepository.existsByEmailIgnoreCase(dto.getEmail()))
                throw new IllegalArgumentException("Student with this email already exists");
                
            student.setEmail(dto.getEmail());
        }

        StudentEntity updatedStudent = studentRepository.save(student);

        return new StudentUpdateResponseDto(
            updatedStudent.getStudentId(),
            updatedStudent.getStudentName(),
            updatedStudent.getEmail(),
            updatedStudent.getVersion()
        );
    }

    @Override
    public void deleteStudent(Long studentId) {
        StudentEntity student = studentRepository.findById(studentId)
                            .orElseThrow(() -> new StudentNotFoundException("Student not found with this id: "+studentId));

        studentRepository.delete(student);
    }    
}
