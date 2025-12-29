package com.siteshkumar.student_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentUpdateResponseDto {
    
    private Long studentId;
    private String studentName;
    private String email;
    private Long version;
}
