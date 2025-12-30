package com.siteshkumar.student_management_system.service;

import com.siteshkumar.student_management_system.dto.CourseCreateRequestDto;
import com.siteshkumar.student_management_system.dto.CourseCreateResponseDto;
import com.siteshkumar.student_management_system.dto.CourseUpdateRequestDto;
import com.siteshkumar.student_management_system.dto.CourseUpdateResponseDto;

public interface CourseService {
    public CourseCreateResponseDto createCourse(CourseCreateRequestDto dto);
    public CourseUpdateResponseDto updateCourse(Long courseId, CourseUpdateRequestDto dto);
}
