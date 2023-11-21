package com.university.erp.repository;

import com.university.erp.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByUserUserId(Long userId);
}
