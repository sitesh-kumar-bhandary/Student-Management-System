package com.siteshkumar.student_management_system.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseUpdateRequestDto {
    
    private String courseName;

    private String code;

    @NotNull(message="version is required for update")
    private Long version;
}
