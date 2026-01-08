package com.siteshkumar.student_management_system.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.siteshkumar.student_management_system.dto.CourseAverageGradeDto;
import com.siteshkumar.student_management_system.dto.StudentAverageGradeDto;
import com.siteshkumar.student_management_system.entity.EnrollmentEntity;

@Repository
public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Long>{

    @Query("""
            SELECT new com.siteshkumar.student_management_system.dto.StudentAverageGradeDto(
            s.studentId,
            s.studentName,
            AVG(e.grade)
            )
            FROM EnrollmentEntity e
            JOIN e.student s
            GROUP BY s.studentId, s.studentName
            """)
    Page<StudentAverageGradeDto> findAverageGradePerStudent(Pageable pageable);

    @Query("""
            SELECT new com.siteshkumar.student_management_system.dto.CourseAverageGradeDto(
            c.courseId,
            c.courseName,
            AVG(e.grade)
            )
            FROM EnrollmentEntity e
            JOIN e.course c
            GROUP BY c.courseId, c.courseName
            """)
    Page<CourseAverageGradeDto> findAverageGradePerCourse(Pageable pageable);
    
    boolean existsByStudentStudentIdAndCourseCourseId(Long studentId, Long courseId);
    Optional<EnrollmentEntity> findByStudentStudentIdAndCourseCourseId(Long studentId, Long courseId);

}
