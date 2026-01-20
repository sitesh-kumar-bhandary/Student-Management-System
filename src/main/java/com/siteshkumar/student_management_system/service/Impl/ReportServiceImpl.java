package com.siteshkumar.student_management_system.service.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.siteshkumar.student_management_system.dto.CourseAverageGradeDto;
import com.siteshkumar.student_management_system.dto.StudentAverageGradeDto;
import com.siteshkumar.student_management_system.repository.EnrollmentRepository;
import com.siteshkumar.student_management_system.service.ReportService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final EnrollmentRepository enrollmentRepository;

    @Override
    public Page<StudentAverageGradeDto> getStudentAverages(Pageable pageable) {

        log.info(
                "Fetching student average grades. Page number: {}, Page size: {}",
                pageable.getPageNumber(),
                pageable.getPageSize());

        Page<StudentAverageGradeDto> result = enrollmentRepository.findAverageGradePerStudent(pageable);

        log.info(
                "Fetched student average grades. Records on page {}: {}",
                result.getNumber(),
                result.getNumberOfElements());

        return result;
    }

    @Override
    public Page<CourseAverageGradeDto> getCourseAverages(Pageable pageable) {

        log.info(
                "Fetching course average grades. Page number: {}, Page size: {}",
                pageable.getPageNumber(),
                pageable.getPageSize());

        Page<CourseAverageGradeDto> result = enrollmentRepository.findAverageGradePerCourse(pageable);

        log.info(
                "Fetched course average grades. Records on page {}: {}",
                result.getNumber(),
                result.getNumberOfElements());

        return result;
    }
}
