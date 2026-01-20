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
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public CourseCreateResponseDto createCourse(CourseCreateRequestDto dto) {

        log.info("Creating course with code: {}", dto.getCode());

        if (courseRepository.existsByCode(dto.getCode())) {
            log.warn("Duplicate course creation attempt with code: {}", dto.getCode());
            throw new DuplicateResourceException(
                    "Course already exist with code : " + dto.getCode());
        }

        CourseEntity course = new CourseEntity();
        course.setCourseName(dto.getCourseName());
        course.setCode(dto.getCode());

        CourseEntity createdCourse = courseRepository.save(course);

        log.info(
                "Course created successfully with id: {}, code: {}",
                createdCourse.getCourseId(),
                createdCourse.getCode());

        return new CourseCreateResponseDto(
                createdCourse.getCourseId(),
                createdCourse.getCourseName(),
                createdCourse.getCode());
    }

    @Override
    public CourseResponseDto updateCourse(Long courseId, CourseUpdateRequestDto dto) {

        log.info("Updating course with id: {}", courseId);

        CourseEntity course = courseRepository.findById(courseId)
                .orElseThrow(() -> {
                    log.warn("Course not found for update with id: {}", courseId);
                    return new ResourceNotFoundException(
                            "Course not found with id : " + courseId);
                });

        if (!course.getVersion().equals(dto.getVersion())) {
            log.warn(
                    "Optimistic locking failure for course id: {}. Expected version: {}, Actual version: {}",
                    courseId,
                    dto.getVersion(),
                    course.getVersion());
            throw new OptimisticLockingFailureException(
                    "Course was already updated by another user. Please refresh!");
        }

        if (dto.getCourseName() != null) {
            log.debug(
                    "Updating course name for course id: {} from '{}' to '{}'",
                    courseId,
                    course.getCourseName(),
                    dto.getCourseName());
            course.setCourseName(dto.getCourseName());
        }

        if (dto.getCode() != null) {
            if (courseRepository.existsByCode(dto.getCode())) {
                log.warn(
                        "Duplicate course code '{}' attempted for course id: {}",
                        dto.getCode(),
                        courseId);
                throw new DuplicateResourceException(
                        "Course already exists with code : " + dto.getCode());
            }

            log.debug(
                    "Updating course code for course id: {} from '{}' to '{}'",
                    courseId,
                    course.getCode(),
                    dto.getCode());
            course.setCode(dto.getCode());
        }

        CourseEntity updatedCourse = courseRepository.save(course);

        log.info(
                "Course updated successfully with id: {}, new version: {}",
                updatedCourse.getCourseId(),
                updatedCourse.getVersion());

        return new CourseResponseDto(
                updatedCourse.getCourseId(),
                updatedCourse.getCourseName(),
                updatedCourse.getCode(),
                updatedCourse.getVersion());
    }

    @Override
    public void deleteCourse(Long courseId) {

        log.info("Deleting course with id: {}", courseId);

        CourseEntity course = courseRepository.findById(courseId)
                .orElseThrow(() -> {
                    log.warn("Course not found for deletion with id: {}", courseId);
                    return new ResourceNotFoundException(
                            "Course not found with id : " + courseId);
                });

        courseRepository.delete(course);

        log.info("Course deleted successfully with id: {}", courseId);
    }

    @Override
    public CourseResponseDto getCourseById(Long courseId) {

        log.info("Fetching course with id: {}", courseId);

        CourseEntity course = courseRepository.findById(courseId)
                .orElseThrow(() -> {
                    log.warn("Course not found with id: {}", courseId);
                    return new ResourceNotFoundException(
                            "Course not found with id : " + courseId);
                });

        return new CourseResponseDto(
                course.getCourseId(),
                course.getCourseName(),
                course.getCode(),
                course.getVersion());
    }

    @Override
    public Page<CourseResponseDto> getAllCourses(Pageable pageable) {

        log.info(
                "Fetching all courses. Page number: {}, Page size: {}",
                pageable.getPageNumber(),
                pageable.getPageSize());

        Page<CourseEntity> currentPage = courseRepository.findAll(pageable);

        Page<CourseResponseDto> courses = currentPage.map(c -> new CourseResponseDto(
                c.getCourseId(),
                c.getCourseName(),
                c.getCode(),
                c.getVersion()));

        log.info(
                "Fetched {} courses on page {}",
                currentPage.getNumberOfElements(),
                currentPage.getNumber());

        return courses;
    }
}
