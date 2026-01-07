package com.siteshkumar.student_management_system.service;

public interface EnrollmentService {
    public void enrollStudent(Long studentId, Long courseId);
    public void removeEnrollment(Long studentId, Long courseId);
}
