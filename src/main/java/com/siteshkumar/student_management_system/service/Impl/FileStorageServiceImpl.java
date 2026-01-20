package com.siteshkumar.student_management_system.service.Impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.siteshkumar.student_management_system.service.FileStorageService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public String uploadStudentProfilePhoto(Long studentId, MultipartFile file) {

        log.info("Profile photo upload requested for studentId: {}", studentId);

        if (file.isEmpty()) {
            log.warn("Empty file upload attempt for studentId: {}", studentId);
            throw new IllegalArgumentException("File can not be empty.");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            log.warn(
                    "Invalid file type '{}' for studentId: {}",
                    contentType,
                    studentId);
            throw new IllegalArgumentException("Only image files are allowed");
        }

        try {
            Files.createDirectories(Paths.get(uploadDir));

            String originalName = Paths.get(file.getOriginalFilename()).getFileName().toString();

            String fileName = "student_" + studentId + "_" + originalName;
            Path filePath = Paths.get(uploadDir).resolve(fileName);

            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING);

            log.info(
                    "Profile photo uploaded successfully for studentId: {}, stored at: {}",
                    studentId,
                    filePath);

            return filePath.toString();

        } catch (IOException ex) {

            log.error(
                    "Failed to upload profile photo for studentId: {}",
                    studentId,
                    ex);
            throw new RuntimeException("Failed to store file", ex);
        }
    }
}
