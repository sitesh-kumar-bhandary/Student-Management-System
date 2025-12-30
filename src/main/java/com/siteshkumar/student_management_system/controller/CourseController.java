package com.siteshkumar.student_management_system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.siteshkumar.student_management_system.dto.CourseCreateRequestDto;
import com.siteshkumar.student_management_system.dto.CourseCreateResponseDto;
import com.siteshkumar.student_management_system.dto.CourseUpdateRequestDto;
import com.siteshkumar.student_management_system.dto.CourseResponseDto;
import com.siteshkumar.student_management_system.service.CourseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;
    
    @PostMapping
    public ResponseEntity<CourseCreateResponseDto> createCourse(@Valid @RequestBody CourseCreateRequestDto dto){
        CourseCreateResponseDto createdCourse = courseService.createCourse(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<CourseResponseDto> updateCourse(@PathVariable Long courseId, @Valid @RequestBody CourseUpdateRequestDto dto){
        CourseResponseDto updatedCourse = courseService.updateCourse(courseId, dto);
        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId){
        courseService.deleteCourse(courseId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseResponseDto> getCourseById(@PathVariable Long courseId){
        CourseResponseDto course = courseService.getCourseById(courseId);
        return ResponseEntity.ok(course);
    }

    // public ResponseEntity<> getAllCourses(){

    // }
}
