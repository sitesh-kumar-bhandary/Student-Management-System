package com.siteshkumar.student_management_system.entity;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="enrollments")
public class EnrollmentEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long enrollmentId;
    
    private Double grade;

    @Column(nullable = false)
    private LocalDate enrollmentDate;

    @Version
    private Long version;

    @ManyToOne
    @JoinColumn(name="studentId", nullable = false)
    private StudentEntity student;

    @ManyToOne
    @JoinColumn(name="courseId", nullable = false)
    private CourseEntity course;
}
