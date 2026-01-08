package com.siteshkumar.student_management_system.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.siteshkumar.student_management_system.dto.CourseAverageGradeDto;
import com.siteshkumar.student_management_system.dto.StudentAverageGradeDto;

public interface ReportService {
    Page<StudentAverageGradeDto> getStudentAverages(Pageable pageable);
    Page<CourseAverageGradeDto> getCourseAverages(Pageable pageable);
}
