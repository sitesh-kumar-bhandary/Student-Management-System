package com.siteshkumar.student_management_system.service.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.siteshkumar.student_management_system.dto.CourseAverageGradeDto;
import com.siteshkumar.student_management_system.dto.StudentAverageGradeDto;
import com.siteshkumar.student_management_system.repository.EnrollmentRepository;
import com.siteshkumar.student_management_system.service.ReportService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{

    private final EnrollmentRepository enrollmentRepository;

    @Override
    public Page<StudentAverageGradeDto> getStudentAverages(Pageable pageable) {
        return enrollmentRepository.findAverageGradePerStudent(pageable);
    }

    @Override
    public Page<CourseAverageGradeDto> getCourseAverages(Pageable pageable) {
        return enrollmentRepository.findAverageGradePerCourse(pageable);
    }
}
