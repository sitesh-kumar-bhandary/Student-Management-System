package com.siteshkumar.student_management_system.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String uploadStudentProfilePhoto(Long studentId, MultipartFile file);
    
}
