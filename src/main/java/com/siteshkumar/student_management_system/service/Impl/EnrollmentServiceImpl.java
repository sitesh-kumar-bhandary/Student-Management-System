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
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Transactional
    @Override
    public void enrollStudent(Long studentId, Long courseId) {

        log.info("Enrollment request received for studentId: {}, courseId: {}", studentId, courseId);

        if (enrollmentRepository
                .existsByStudentStudentIdAndCourseCourseId(studentId, courseId)) {

            log.warn(
                    "Duplicate enrollment attempt for studentId: {}, courseId: {}",
                    studentId,
                    courseId);
            throw new DuplicateResourceException(
                    "Student already enrolled in this course");
        }

        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> {
                    log.warn("Student not found for enrollment, studentId: {}", studentId);
                    return new ResourceNotFoundException("Student not found");
                });

        CourseEntity course = courseRepository.findById(courseId)
                .orElseThrow(() -> {
                    log.warn("Course not found for enrollment, courseId: {}", courseId);
                    return new ResourceNotFoundException("Course not found");
                });

        EnrollmentEntity enrollment = new EnrollmentEntity();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(LocalDate.now());

        enrollmentRepository.save(enrollment);

        log.info(
                "Student enrolled successfully. studentId: {}, courseId: {}, enrollmentDate: {}",
                studentId,
                courseId,
                enrollment.getEnrollmentDate());
    }

    @Transactional
    @Override
    public void removeEnrollment(Long studentId, Long courseId) {

        log.info(
                "Remove enrollment request received for studentId: {}, courseId: {}",
                studentId,
                courseId);

        EnrollmentEntity enrollment = enrollmentRepository
                .findByStudentStudentIdAndCourseCourseId(studentId, courseId)
                .orElseThrow(() -> {
                    log.warn(
                            "Enrollment not found for removal. studentId: {}, courseId: {}",
                            studentId,
                            courseId);
                    return new ResourceNotFoundException("Enrollment not found!!!");
                });

        enrollmentRepository.delete(enrollment);

        log.info(
                "Enrollment removed successfully. studentId: {}, courseId: {}",
                studentId,
                courseId);
    }

    @Transactional
    @Override
    public EnrollmentGradeResponseDto updateGrade(Long enrollmentId, Double grade) {

        log.info(
                "Grade update request received for enrollmentId: {}, newGrade: {}",
                enrollmentId,
                grade);

        EnrollmentEntity enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> {
                    log.warn("Enrollment not found for grade update, enrollmentId: {}", enrollmentId);
                    return new ResourceNotFoundException("Enrollment not found");
                });

        enrollment.setGrade(grade);

        log.info(
                "Grade updated successfully for enrollmentId: {}, studentId: {}, courseId: {}, grade: {}",
                enrollment.getEnrollmentId(),
                enrollment.getStudent().getStudentId(),
                enrollment.getCourse().getCourseId(),
                enrollment.getGrade());

        return new EnrollmentGradeResponseDto(
                enrollment.getEnrollmentId(),
                enrollment.getStudent().getStudentId(),
                enrollment.getStudent().getStudentName(),
                enrollment.getCourse().getCourseId(),
                enrollment.getCourse().getCourseName(),
                enrollment.getGrade());
    }
}
