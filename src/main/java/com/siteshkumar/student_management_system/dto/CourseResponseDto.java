package com.siteshkumar.student_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponseDto {
    
    private Long courseId;
    private String courseName;
    private String code;
    private Long version;
}
