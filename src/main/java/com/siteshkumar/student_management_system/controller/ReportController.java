package com.siteshkumar.student_management_system.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siteshkumar.student_management_system.dto.CourseAverageGradeDto;
import com.siteshkumar.student_management_system.dto.StudentAverageGradeDto;
import com.siteshkumar.student_management_system.service.ReportService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportController {
    
    private final ReportService reportService;

    @GetMapping("/students/average-grade")
    public ResponseEntity<Page<StudentAverageGradeDto>> getStudentAverages(Pageable pageable){
        Page<StudentAverageGradeDto> page = reportService.getStudentAverages(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/courses/average-grade")
    public ResponseEntity<Page<CourseAverageGradeDto>> getCourseAverages(Pageable pageable){
        Page<CourseAverageGradeDto> page = reportService.getCourseAverages(pageable);
        return ResponseEntity.ok(page);
    }
}
