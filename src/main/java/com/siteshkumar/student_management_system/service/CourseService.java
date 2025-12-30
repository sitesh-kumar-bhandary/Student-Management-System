package com.siteshkumar.student_management_system.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.siteshkumar.student_management_system.dto.CourseCreateRequestDto;
import com.siteshkumar.student_management_system.dto.CourseCreateResponseDto;
import com.siteshkumar.student_management_system.dto.CourseUpdateRequestDto;
import com.siteshkumar.student_management_system.dto.CourseResponseDto;

public interface CourseService {
    public CourseCreateResponseDto createCourse(CourseCreateRequestDto dto);
    public CourseResponseDto updateCourse(Long courseId, CourseUpdateRequestDto dto);
    public void deleteCourse(Long courseId);
    public CourseResponseDto getCourseById(Long courseId);
    public Page<CourseResponseDto> getAllCourses(Pageable pageable);
}
