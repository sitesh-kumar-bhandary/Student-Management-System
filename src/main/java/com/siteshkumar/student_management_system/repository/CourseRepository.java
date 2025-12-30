package com.siteshkumar.student_management_system.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.siteshkumar.student_management_system.entity.CourseEntity;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long>{

    @Query("""
            select e.course from EnrollmentEntity e where e.student.studentId = :studentId
            """)
    List<CourseEntity> findCourseByStudentId(Long studentId);

    boolean existsByCode(String code);
}
