package com.siteshkumar.student_management_system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCreateRequestDto {
    
    @NotBlank(message="student name is required")
    private String studentName;

    @Email(message="Invalid email format")
    @NotBlank(message="email is required")
    private String email;
}
