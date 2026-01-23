package com.siteshkumar.student_management_system.service.Impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.siteshkumar.student_management_system.dto.CourseAverageGradeDto;
import com.siteshkumar.student_management_system.dto.StudentAverageGradeDto;
import com.siteshkumar.student_management_system.repository.EnrollmentRepository;

@ExtendWith(MockitoExtension.class)
public class ReportServiceImplTest {
    
    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private ReportServiceImpl reportServiceImpl;

    @Test
    void getStudentAveragesTest(){
        PageRequest pageable = PageRequest.of(0, 10);

        StudentAverageGradeDto dto = new StudentAverageGradeDto(1L, "Sitesh", 9.0);

        Page<StudentAverageGradeDto> page = new PageImpl<>(List.of(dto), pageable, 1);

        when(enrollmentRepository.findAverageGradePerStudent(pageable)).thenReturn(page);

        Page<StudentAverageGradeDto> result = reportServiceImpl.getStudentAverages(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("Sitesh", result.getContent().get(0).getStudentName());

        verify(enrollmentRepository).findAverageGradePerStudent(pageable);
    }

    @Test
    void getCoursesAverageTest(){
        PageRequest pageable = PageRequest.of(0, 10);

        CourseAverageGradeDto dto = new CourseAverageGradeDto(1L, "Java", 8.9);

        Page<CourseAverageGradeDto> page = new PageImpl<>(List.of(dto), pageable, 1);

        when(enrollmentRepository.findAverageGradePerCourse(pageable)).thenReturn(page);

        Page<CourseAverageGradeDto> result = reportServiceImpl.getCourseAverages(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("Java", result.getContent().get(0).getCourseName());

        verify(enrollmentRepository).findAverageGradePerCourse(pageable);
    }
}
