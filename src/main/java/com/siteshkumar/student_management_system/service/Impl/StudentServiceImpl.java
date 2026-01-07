package com.siteshkumar.student_management_system.service.Impl;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.siteshkumar.student_management_system.dto.StudentCreateRequestDto;
import com.siteshkumar.student_management_system.dto.StudentCreateResponseDto;
import com.siteshkumar.student_management_system.dto.StudentResponseDto;
import com.siteshkumar.student_management_system.dto.StudentUpdateRequestDto;
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
    public StudentResponseDto updateStudent(Long studentId, StudentUpdateRequestDto dto) {
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

        return new StudentResponseDto(
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

    @Override
    public StudentResponseDto getStudentById(Long studentId) {
        StudentEntity student = studentRepository.findById(studentId)
                            .orElseThrow(() -> new StudentNotFoundException("Student not found with this id: "+studentId));

        return new StudentResponseDto(
            student.getStudentId(),
            student.getStudentName(),
            student.getEmail(),
            student.getVersion()
        );
    }

    @Override
    public Page<StudentResponseDto> getAllStudents(Pageable pageable) {
        Page<StudentEntity> studentPage = studentRepository.findAll(pageable);

        Page<StudentResponseDto> students = studentPage.map(student -> new StudentResponseDto(
                                        student.getStudentId(),
                                        student.getStudentName(),
                                        student.getEmail(),
                                        student.getVersion()
                                    ));
        
        return students;
    }

    @Override
    public Page<StudentResponseDto> searchStudents(String studentName, String email, Pageable pageable) {
        return studentRepository.searchStudents(studentName, email, pageable)
                                .map(student -> {
                                    StudentResponseDto dto = new StudentResponseDto();
                                    dto.setStudentId(student.getStudentId());
                                    dto.setStudentName(student.getStudentName());
                                    dto.setEmail(student.getEmail());
                                    return dto;
                                });
    }    
}
