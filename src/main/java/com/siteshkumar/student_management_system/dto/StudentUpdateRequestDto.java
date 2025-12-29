package com.siteshkumar.student_management_system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentUpdateRequestDto {
    
    private String studentName;

    @Email(message="Invalid email format")
    private String email;

    @NotNull(message="Version is required to update")
    private Long version;
}
