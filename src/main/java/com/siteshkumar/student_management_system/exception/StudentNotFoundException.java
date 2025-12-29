package com.siteshkumar.student_management_system.exception;

public class StudentNotFoundException extends RuntimeException {
    
    public StudentNotFoundException(String message){
        super(message);
    }
}
