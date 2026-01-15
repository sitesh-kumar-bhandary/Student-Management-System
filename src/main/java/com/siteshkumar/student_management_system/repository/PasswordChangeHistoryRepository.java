package com.siteshkumar.student_management_system.repository;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import com.siteshkumar.student_management_system.entity.PasswordChangeHistoryEntity;
import com.siteshkumar.student_management_system.entity.UserEntity;

public interface PasswordChangeHistoryRepository extends JpaRepository<PasswordChangeHistoryEntity, Long>{
    long countByUserAndChangedAtAfter(UserEntity user, LocalDateTime after);
}
