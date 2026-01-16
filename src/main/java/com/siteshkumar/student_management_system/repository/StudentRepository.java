package com.siteshkumar.student_management_system.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.siteshkumar.student_management_system.entity.StudentEntity;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long>{
    
    @Query("""
        SELECT s FROM StudentEntity s
        WHERE (:studentName IS NULL OR LOWER(s.studentName) LIKE LOWER(CONCAT(:studentName, '%')))
        AND (:email IS NULL OR LOWER(s.email) = LOWER(:email))
    """)
    Page<StudentEntity> searchStudents(String studentName, String email, Pageable pageable);
    
    Page<StudentEntity> findByStudentName(String studentName, Pageable pageable);
    boolean existsByEmailIgnoreCase(String email);
    Optional<StudentEntity> findByEmail(String email);
}
