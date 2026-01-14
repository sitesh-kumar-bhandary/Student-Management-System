package com.siteshkumar.student_management_system.service.Impl;

import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.siteshkumar.student_management_system.dto.EnrollmentGradeResponseDto;
import com.siteshkumar.student_management_system.entity.CourseEntity;
import com.siteshkumar.student_management_system.entity.EnrollmentEntity;
import com.siteshkumar.student_management_system.entity.StudentEntity;
import com.siteshkumar.student_management_system.exception.DuplicateResourceException;
import com.siteshkumar.student_management_system.exception.ResourceNotFoundException;
import com.siteshkumar.student_management_system.repository.CourseRepository;
import com.siteshkumar.student_management_system.repository.EnrollmentRepository;
import com.siteshkumar.student_management_system.repository.StudentRepository;
import com.siteshkumar.student_management_system.service.EnrollmentService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService{
    
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Transactional
    @Override
    public void enrollStudent(Long studentId, Long courseId) {
        if(enrollmentRepository.existsByStudentStudentIdAndCourseCourseId(studentId, courseId))
            throw new DuplicateResourceException("Student already enrolled in this course");

        StudentEntity student = studentRepository.findById(studentId)
                                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        CourseEntity course = courseRepository.findById(courseId)
                                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        EnrollmentEntity enrollment = new EnrollmentEntity();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(LocalDate.now());

        enrollmentRepository.save(enrollment);
    }

    @Transactional
    @Override
    public void removeEnrollment(Long studentId, Long courseId) {
        EnrollmentEntity enrollment = enrollmentRepository
                                    .findByStudentStudentIdAndCourseCourseId(studentId, courseId)
                                    .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found!!!"));

        enrollmentRepository.delete(enrollment);
    }

    @Transactional
    @Override
    public EnrollmentGradeResponseDto updateGrade(Long enrollmentId, Double grade) {
        EnrollmentEntity enrollment = enrollmentRepository
                                    .findById(enrollmentId)
                                    .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found")); 

        enrollment.setGrade(grade);

        return new EnrollmentGradeResponseDto(
            enrollment.getEnrollmentId(),
            enrollment.getStudent().getStudentId(),
            enrollment.getStudent().getStudentName(),
            enrollment.getCourse().getCourseId(),
            enrollment.getCourse().getCourseName(),
            enrollment.getGrade()
        );
    }
}
