package com.siteshkumar.student_management_system.service.Impl;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.siteshkumar.student_management_system.dto.StudentCreateRequestDto;
import com.siteshkumar.student_management_system.dto.StudentCreateResponseDto;
import com.siteshkumar.student_management_system.dto.StudentResponseDto;
import com.siteshkumar.student_management_system.dto.StudentUpdateRequestDto;
import com.siteshkumar.student_management_system.entity.Role;
import com.siteshkumar.student_management_system.entity.StudentEntity;
import com.siteshkumar.student_management_system.entity.UserEntity;
import com.siteshkumar.student_management_system.exception.ResourceNotFoundException;
import com.siteshkumar.student_management_system.exception.StudentNotFoundException;
import com.siteshkumar.student_management_system.repository.StudentRepository;
import com.siteshkumar.student_management_system.repository.UserRepository;
import com.siteshkumar.student_management_system.security.AuthUtils;
import com.siteshkumar.student_management_system.security.CustomUserDetails;
import com.siteshkumar.student_management_system.service.EmailService;
import com.siteshkumar.student_management_system.service.FileStorageService;
import com.siteshkumar.student_management_system.service.StudentService;
import com.siteshkumar.student_management_system.utils.PasswordUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthUtils authUtils;
    private final FileStorageService fileStorageService;

    @Override
    public StudentCreateResponseDto createStudent(StudentCreateRequestDto dto) {

        log.info("Creating student with email: {}", dto.getEmail());

        if (userRepository.existsByEmail(dto.getEmail())) {
            log.warn("Student creation failed. Email already exists: {}", dto.getEmail());
            throw new IllegalArgumentException("Email already exists");
        }

        String rawPassword = PasswordUtils.generateTemporaryPassword();

        StudentEntity student = new StudentEntity();
        student.setStudentName(dto.getStudentName());
        student.setEmail(dto.getEmail());

        UserEntity user = new UserEntity();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(Role.STUDENT);
        user.setStudent(student);

        userRepository.save(user);

        log.info(
                "Student created successfully. studentId: {}, email: {}",
                student.getStudentId(),
                student.getEmail());

        emailService.sendStudentCreatedEmail(
                dto.getEmail(),
                dto.getStudentName(),
                rawPassword);

        log.info("Student creation email triggered for email: {}", dto.getEmail());

        return new StudentCreateResponseDto(
                student.getStudentId(),
                student.getStudentName(),
                student.getEmail());
    }

    @Override
    public StudentResponseDto updateStudent(Long studentId, StudentUpdateRequestDto dto) {

        log.info("Updating student with id: {}", studentId);

        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> {
                    log.warn("Student not found for update. studentId: {}", studentId);
                    return new StudentNotFoundException(
                            "Student not found with id: " + studentId);
                });

        if (dto.getVersion() == null || !student.getVersion().equals(dto.getVersion())) {
            log.warn(
                    "Optimistic locking failure while updating studentId: {}. Expected version: {}, Actual version: {}",
                    studentId,
                    dto.getVersion(),
                    student.getVersion());
            throw new OptimisticLockingFailureException(
                    "Student was already updated by another user. Please refresh!");
        }

        if (dto.getStudentName() != null) {
            log.debug(
                    "Updating student name for studentId: {} from '{}' to '{}'",
                    studentId,
                    student.getStudentName(),
                    dto.getStudentName());
            student.setStudentName(dto.getStudentName());
        }

        if (dto.getEmail() != null && !dto.getEmail().equalsIgnoreCase(student.getEmail())) {

            if (studentRepository.existsByEmailIgnoreCase(dto.getEmail())) {
                log.warn(
                        "Email update failed. Email already exists: {}",
                        dto.getEmail());
                throw new IllegalArgumentException(
                        "Student with this email already exists");
            }

            log.debug(
                    "Updating email for studentId: {} from '{}' to '{}'",
                    studentId,
                    student.getEmail(),
                    dto.getEmail());
            student.setEmail(dto.getEmail());
        }

        StudentEntity updatedStudent = studentRepository.save(student);

        log.info(
                "Student updated successfully. studentId: {}, version: {}",
                updatedStudent.getStudentId(),
                updatedStudent.getVersion());

        return new StudentResponseDto(
                updatedStudent.getStudentId(),
                updatedStudent.getStudentName(),
                updatedStudent.getEmail(),
                updatedStudent.getVersion());
    }

    @Override
    public void deleteStudent(Long studentId) {

        log.info("Deleting student with id: {}", studentId);

        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> {
                    log.warn("Student not found for deletion. studentId: {}", studentId);
                    return new StudentNotFoundException(
                            "Student not found with this id: " + studentId);
                });

        studentRepository.delete(student);

        log.info("Student deleted successfully. studentId: {}", studentId);
    }

    @Transactional
    @Override
    public StudentResponseDto getStudentById(Long studentId) {

        log.info("Fetching student with id: {}", studentId);

        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> {
                    log.warn("Student not found with id: {}", studentId);
                    return new StudentNotFoundException(
                            "Student not found with this id: " + studentId);
                });

        return new StudentResponseDto(
                student.getStudentId(),
                student.getStudentName(),
                student.getEmail(),
                student.getVersion());
    }

    @Transactional
    @Override
    public Page<StudentResponseDto> getAllStudents(Pageable pageable) {

        log.info(
                "Fetching all students. Page number: {}, Page size: {}",
                pageable.getPageNumber(),
                pageable.getPageSize());

        Page<StudentEntity> studentPage = studentRepository.findAll(pageable);

        log.info(
                "Fetched {} students on page {}",
                studentPage.getNumberOfElements(),
                studentPage.getNumber());

        return studentPage.map(student -> new StudentResponseDto(
                student.getStudentId(),
                student.getStudentName(),
                student.getEmail(),
                student.getVersion()));
    }

    @Transactional
    @Override
    public Page<StudentResponseDto> searchStudents(
            String studentName,
            String email,
            Pageable pageable) {

        log.info(
                "Searching students. Name: {}, Email: {}, Page number: {}, Page size: {}",
                studentName,
                email,
                pageable.getPageNumber(),
                pageable.getPageSize());

        return studentRepository.searchStudents(studentName, email, pageable)
                .map(student -> {
                    StudentResponseDto dto = new StudentResponseDto();
                    dto.setStudentId(student.getStudentId());
                    dto.setStudentName(student.getStudentName());
                    dto.setEmail(student.getEmail());
                    return dto;
                });
    }

    @Transactional
    @Override
    public StudentResponseDto getMyProfile() {

        CustomUserDetails user = authUtils.getCurrentLoggedInUser();

        log.info("Fetching profile for logged-in user: {}", user.getUsername());

        StudentEntity student = studentRepository.findByEmail(user.getUsername())
                .orElseThrow(() -> {
                    log.warn(
                            "Student profile not found for email: {}",
                            user.getUsername());
                    return new ResourceNotFoundException(
                            "Student profile not found");
                });

        return new StudentResponseDto(
                student.getStudentId(),
                student.getStudentName(),
                student.getEmail(),
                student.getVersion());
    }

    @Transactional
    @Override
    public void uploadStudentPhoto(Long studentId, MultipartFile file) {

        log.info("Uploading profile photo for studentId: {}", studentId);

        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> {
                    log.warn(
                            "Student not found for profile photo upload. studentId: {}",
                            studentId);
                    return new ResourceNotFoundException("Student not found");
                });

        String imagePath = fileStorageService.uploadStudentProfilePhoto(studentId, file);

        student.setProfileImageUrl(imagePath);

        log.info(
                "Profile photo uploaded successfully for studentId: {}",
                studentId);
    }
}
