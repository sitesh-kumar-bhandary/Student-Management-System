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
@Table(name="students")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long studentId;

    @Column(nullable = false)
    private String studentName;

    @Column(nullable=false, unique=true)
    private String email;

    @Version
    private Long version;

    @OneToMany(mappedBy="student", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<EnrollmentEntity> enrollments;
}
