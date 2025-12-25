package com.siteshkumar.student_management_system.entity;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="courses")
public class CourseEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long courseId;

    @Column(nullable=false)
    private String courseName;

    @Column(nullable = false, unique = true)
    private String code;

    @Version
    private Long version;

    @OneToMany(mappedBy="course", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<EnrollmentEntity> enrollments;
}
