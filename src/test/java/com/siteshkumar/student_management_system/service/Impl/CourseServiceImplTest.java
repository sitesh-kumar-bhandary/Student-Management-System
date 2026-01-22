package com.siteshkumar.student_management_system.service.Impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

import com.siteshkumar.student_management_system.dto.CourseCreateRequestDto;
import com.siteshkumar.student_management_system.dto.CourseUpdateRequestDto;
import com.siteshkumar.student_management_system.entity.CourseEntity;
import com.siteshkumar.student_management_system.exception.DuplicateResourceException;
import com.siteshkumar.student_management_system.exception.ResourceNotFoundException;
import com.siteshkumar.student_management_system.repository.CourseRepository;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {
    
    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseServiceImpl;

    @Test
    void createCourse_success(){
        CourseCreateRequestDto dto = new CourseCreateRequestDto("Java", "JAVA101");

        when(courseRepository.existsByCode("JAVA101")).thenReturn(false);

        CourseEntity saved = new CourseEntity();
        saved.setCourseId(1L);
        saved.setCourseName("Java");
        saved.setCode("JAVA101");

        when(courseRepository.save(any(CourseEntity.class))).thenReturn(saved);

        var response = courseServiceImpl.createCourse(dto);

        assertEquals("Java", response.getCourseName());
        assertEquals("JAVA101", response.getCode());
        verify(courseRepository).save(any(CourseEntity.class));
    }

    @Test
    void createCourse_duplicateCode_throwsException(){
        CourseCreateRequestDto dto = new CourseCreateRequestDto("Java", "JAVA101");

        when(courseRepository.existsByCode("JAVA101")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> courseServiceImpl.createCourse(dto));

        verify(courseRepository, never()).save(any());
    }

    @Test
    void updateCourse_success(){
        CourseEntity course = new CourseEntity();
        course.setCourseId(1L);
        course.setCourseName("Java");
        course.setCode("JAVA101");
        course.setVersion(1L);

        CourseUpdateRequestDto dto = new CourseUpdateRequestDto("Advance Java", "JAVA201", 1L);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        when(courseRepository.existsByCode("JAVA201")).thenReturn(false);

        when(courseRepository.save(course)).thenReturn(course);

        var response = courseServiceImpl.updateCourse(1L, dto);

        assertEquals("Advance Java", response.getCourseName());
        assertEquals("JAVA201", response.getCode());
    }

    @Test
    void updateCourse_notFound(){

        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        CourseUpdateRequestDto dto = new CourseUpdateRequestDto("Java", null, 1L);

        assertThrows(ResourceNotFoundException.class, () -> courseServiceImpl.updateCourse(1L, dto));
    }

    @Test
    void updateCourse_versionMismatch_throwsException(){
        CourseEntity course = new CourseEntity();
        course.setCourseId(1L);
        course.setVersion(2L);

        CourseUpdateRequestDto dto = new CourseUpdateRequestDto("Java", null, 1L);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        assertThrows(OptimisticLockingFailureException.class, () -> courseServiceImpl.updateCourse(1L, dto));
    }

    @Test
    void updateCourse_duplicateCode_throwsException(){
        CourseEntity course = new CourseEntity();
        course.setCourseId(1L);
        course.setVersion(1L);

        CourseUpdateRequestDto dto = new CourseUpdateRequestDto(null, "JAVA101", 1L);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        when(courseRepository.existsByCode("JAVA101")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> courseServiceImpl.updateCourse(1L, dto));
    }

    @Test
    void deleteCourse_success(){
        CourseEntity course = new CourseEntity();
        course.setCourseId(1L);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        courseServiceImpl.deleteCourse(1L);

        verify(courseRepository).delete(course);
    }

    @Test
    void deleteCourse_notFound(){
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courseServiceImpl.deleteCourse(1L));
    }

    @Test
    void getCourseById_success(){
        CourseEntity course = new CourseEntity();
        course.setCourseId(1L);
        course.setCourseName("Java");
        course.setCode("JAVA101");
        course.setVersion(1L);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        var response = courseServiceImpl.getCourseById(1L);

        assertEquals("Java", response.getCourseName());
    }

    @Test
    void getCourseById_notFound(){
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courseServiceImpl.getCourseById(1L));
    }

    @Test
    void getAllCourses_success(){
        CourseEntity course = new CourseEntity();
        course.setCourseId(1L);
        course.setCourseName("Java");
        course.setCode("JAVA101");
        course.setVersion(1L);

        Page<CourseEntity> page = new PageImpl<>(List.of(course));

        when(courseRepository.findAll(any(PageRequest.class))).thenReturn(page);

        Page<?> result = courseServiceImpl.getAllCourses(PageRequest.of(0, 10));

        assertEquals(1, result.getTotalElements());
    }
}
