package com.siteshkumar.student_management_system.dto;

import com.siteshkumar.student_management_system.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupResponseDto {
    
    private Long id;
    private String email;
    private Role role;
    
}
