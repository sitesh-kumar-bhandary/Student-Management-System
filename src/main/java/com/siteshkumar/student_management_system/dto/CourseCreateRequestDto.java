package com.siteshkumar.student_management_system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseCreateRequestDto {
    
    @NotBlank(message="Course name is required")
    private String courseName;

    @NotBlank(message="Course code is required")
    private String code;
}
