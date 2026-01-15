package com.siteshkumar.student_management_system.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequestDto {
    
    @NotBlank(message="Old password is required")
    private String oldPassword;

    @NotBlank(message="New Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String newPassword;

    @AssertTrue(message="New password must be different from old password")
    public boolean isNewPasswordDifferent(){
        return !oldPassword.equals(newPassword);
    }
}
