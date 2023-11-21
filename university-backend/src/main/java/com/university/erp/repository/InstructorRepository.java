package com.university.erp.repository;

import com.university.erp.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    // You can define custom query methods here if needed
}
