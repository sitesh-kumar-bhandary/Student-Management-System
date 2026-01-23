package com.siteshkumar.student_management_system.service.Impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.siteshkumar.student_management_system.dto.StudentCreateRequestDto;
import com.siteshkumar.student_management_system.dto.StudentCreateResponseDto;
import com.siteshkumar.student_management_system.dto.StudentResponseDto;
import com.siteshkumar.student_management_system.dto.StudentUpdateRequestDto;
import com.siteshkumar.student_management_system.entity.StudentEntity;
import com.siteshkumar.student_management_system.entity.UserEntity;
import com.siteshkumar.student_management_system.exception.ResourceNotFoundException;
import com.siteshkumar.student_management_system.exception.StudentNotFoundException;
import com.siteshkumar.student_management_system.repository.StudentRepository;
import com.siteshkumar.student_management_system.repository.UserRepository;
import com.siteshkumar.student_management_system.security.AuthUtils;
import com.siteshkumar.student_management_system.security.CustomUserDetails;
import com.siteshkumar.student_management_system.service.EmailService;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {
    
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailService emailService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private AuthUtils authUtils;

    @InjectMocks
    private StudentServiceImpl studentServiceImpl;
    
    @Test
    void createStudent_success(){
        StudentCreateRequestDto dto = new StudentCreateRequestDto("Sitesh", "test@gmail.com");

        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);

        when(passwordEncoder.encode(anyString())).thenReturn("encoded");

        StudentCreateResponseDto response = studentServiceImpl.createStudent(dto);

        assertEquals("Sitesh", response.getStudentName());
        assertEquals("test@gmail.com", response.getEmail());

        verify(userRepository).save(any(UserEntity.class));
        verify(emailService).sendStudentCreatedEmail(eq("test@gmail.com"), eq("Sitesh"), anyString());
    }

    @Test
    void createStudent_emailAlreadyExists(){
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, 
            () -> studentServiceImpl.createStudent(new StudentCreateRequestDto("A", "a@gmail.com")));

            verify(userRepository, never()).save(any());
    }

    @Test
    void updateStudent_success(){
        StudentEntity student = new StudentEntity();
        student.setStudentId(1L);
        student.setEmail("old@gmail.com");
        student.setStudentName("Old");
        student.setVersion(1L);

        StudentUpdateRequestDto dto = new StudentUpdateRequestDto("New", "new@gmail.com", 1L);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        when(studentRepository.existsByEmailIgnoreCase("new@gmail.com")).thenReturn(false);

        when(studentRepository.save(student)).thenReturn(student);

        StudentResponseDto response = studentServiceImpl.updateStudent(1L, dto);

        assertEquals("New", response.getStudentName());
        assertEquals("new@gmail.com", response.getEmail());
    }

    @Test
    void updateStudent_notFound(){
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, 
            () -> studentServiceImpl.updateStudent(1L, new StudentUpdateRequestDto()));
    }

    @Test
    void updateStudent_versionMismatch(){
        StudentEntity student = new StudentEntity();
        student.setVersion(2L);

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        StudentUpdateRequestDto dto = new StudentUpdateRequestDto("A", null, 1L);

        assertThrows(OptimisticLockingFailureException.class, 
            () -> studentServiceImpl.updateStudent(1L, dto));
    }

    @Test
    void updateStudent_duplicateEmail(){
        StudentEntity student = new StudentEntity();
        student.setVersion(1L);
        student.setEmail("old@gmail.com");

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        when(studentRepository.existsByEmailIgnoreCase("new@gmail.com")).thenReturn(true);

        StudentUpdateRequestDto dto = new StudentUpdateRequestDto(null, "new@gmail.com", 1L);

        assertThrows(IllegalArgumentException.class, 
            () -> studentServiceImpl.updateStudent(1L, dto));
    }

     @Test
    void deleteStudent_success() {
        StudentEntity student = new StudentEntity();
        student.setStudentId(1L);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        studentServiceImpl.deleteStudent(1L);

        verify(studentRepository).delete(student);
    }

    @Test
    void deleteStudent_notFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentServiceImpl.deleteStudent(1L));
    }

    @Test
    void getStudentById_success() {
        StudentEntity student = new StudentEntity();
        student.setStudentId(1L);
        student.setStudentName("Sitesh");
        student.setEmail("a@gmail.com");
        student.setVersion(1L);

        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(student));

        StudentResponseDto dto =
                studentServiceImpl.getStudentById(1L);

        assertEquals("Sitesh", dto.getStudentName());
        assertEquals("a@gmail.com", dto.getEmail());
    }

    @Test
    void getStudentById_notFound() {
        when(studentRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class,
                () -> studentServiceImpl.getStudentById(1L));
    }

    @Test
    void getAllStudents_success() {
        StudentEntity student = new StudentEntity();
        student.setStudentId(1L);
        student.setEmail("a@gmail.com");

        Page<StudentEntity> page = new PageImpl<>(List.of(student));

        when(studentRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<StudentResponseDto> result = studentServiceImpl.getAllStudents(PageRequest.of(0, 10));

        assertEquals(1, result.getTotalElements());
    }

    @Test
    void searchStudents_success() {
        StudentEntity student = new StudentEntity();
        student.setStudentId(1L);
        student.setEmail("a@gmail.com");

        Page<StudentEntity> page = new PageImpl<>(List.of(student));

        when(studentRepository.searchStudents(any(), any(), any())).thenReturn(page);

        Page<StudentResponseDto> result = studentServiceImpl.
                                        searchStudents("Sitesh", "a@gmail.com", PageRequest.of(0, 10));

        assertEquals(1, result.getTotalElements());
        assertEquals("a@gmail.com", result.getContent().get(0).getEmail());
    }

     @Test
    void getMyProfile_success() {
        CustomUserDetails user = mock(CustomUserDetails.class);

        when(user.getUsername()).thenReturn("test@gmail.com");

        StudentEntity student = new StudentEntity();
        student.setEmail("test@gmail.com");

        when(authUtils.getCurrentLoggedInUser()).thenReturn(user);

        when(studentRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(student));

        StudentResponseDto dto = studentServiceImpl.getMyProfile();

        assertEquals("test@gmail.com", dto.getEmail());
    }

    @Test
    void getMyProfile_notFound() {
        CustomUserDetails user = mock(CustomUserDetails.class);

        when(user.getUsername()).thenReturn("x@gmail.com");

        when(authUtils.getCurrentLoggedInUser()).thenReturn(user);

        when(studentRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> studentServiceImpl.getMyProfile());
    }
}
