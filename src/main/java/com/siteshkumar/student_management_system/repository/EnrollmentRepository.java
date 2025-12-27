package com.siteshkumar.student_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.siteshkumar.student_management_system.entity.EnrollmentEntity;

@Repository
public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Long>{

}
