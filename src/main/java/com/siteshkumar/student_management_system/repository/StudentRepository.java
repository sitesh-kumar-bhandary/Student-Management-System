package com.siteshkumar.student_management_system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.siteshkumar.student_management_system.entity.StudentEntity;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long>{
    
    Page<StudentEntity> findByStudentName(String studentName, Pageable pageable);
}
