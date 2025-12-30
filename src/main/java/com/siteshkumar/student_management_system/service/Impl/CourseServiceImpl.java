package com.siteshkumar.student_management_system.service.Impl;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.siteshkumar.student_management_system.dto.CourseCreateRequestDto;
import com.siteshkumar.student_management_system.dto.CourseCreateResponseDto;
import com.siteshkumar.student_management_system.dto.CourseUpdateRequestDto;
import com.siteshkumar.student_management_system.dto.CourseResponseDto;
import com.siteshkumar.student_management_system.entity.CourseEntity;
import com.siteshkumar.student_management_system.exception.DuplicateResourceException;
import com.siteshkumar.student_management_system.exception.ResourceNotFoundException;
import com.siteshkumar.student_management_system.repository.CourseRepository;
import com.siteshkumar.student_management_system.service.CourseService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService{

    private final CourseRepository courseRepository;

    @Override
    public CourseCreateResponseDto createCourse(CourseCreateRequestDto dto) {
        if(courseRepository.existsByCode(dto.getCode()))
            throw new DuplicateResourceException("Course already exist with code : "+dto.getCode());
        
        CourseEntity course = new CourseEntity();
        course.setCourseName(dto.getCourseName());
        course.setCode(dto.getCode());

        CourseEntity createdCourse = courseRepository.save(course);
        
        return new CourseCreateResponseDto(
            createdCourse.getCourseId(),
            createdCourse.getCourseName(),
            createdCourse.getCode()
        );
    }

    @Override
    public CourseResponseDto updateCourse(Long courseId, CourseUpdateRequestDto dto) {
        CourseEntity course = courseRepository.findById(courseId)
                                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id : "+courseId));

        if(! course.getVersion().equals(dto.getVersion()))
            throw new OptimisticLockingFailureException("Course was already updated by another user. Please refresh!");

        if(dto.getCourseName() != null)
            course.setCourseName(dto.getCourseName());

        if(dto.getCode() != null) {
            if(courseRepository.existsByCode(dto.getCode()))
                throw new DuplicateResourceException("Course already exists with code : " + dto.getCode());

            course.setCode(dto.getCode());
        }

        CourseEntity updatedCourse = courseRepository.save(course);

        return new CourseResponseDto(
            updatedCourse.getCourseId(),
            updatedCourse.getCourseName(),
            updatedCourse.getCode(),
            updatedCourse.getVersion()
        ); 
    }

    @Override
    public void deleteCourse(Long courseId) {
        CourseEntity course = courseRepository.findById(courseId)
                                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id : "+courseId));

        courseRepository.delete(course);
    }

    @Override
    public CourseResponseDto getCourseById(Long courseId) {
        CourseEntity course = courseRepository.findById(courseId)
                            .orElseThrow(() -> new ResourceNotFoundException("Course not found with id : "+courseId));

        return new CourseResponseDto(
            course.getCourseId(),
            course.getCourseName(),
            course.getCode(),
            course.getVersion()
        );
    }

    @Override
    public Page<CourseResponseDto> getAllCourses(Pageable pageable) {
        Page<CourseEntity> currentPage = courseRepository.findAll(pageable);

        Page<CourseResponseDto> courses = currentPage.map(c -> new CourseResponseDto(
            c.getCourseId(),
            c.getCourseName(),
            c.getCode(),
            c.getVersion()
        ));

        return courses;
    }
}
