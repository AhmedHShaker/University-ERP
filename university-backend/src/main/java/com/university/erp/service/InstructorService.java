package com.university.erp.service;

import com.university.erp.model.Instructor;
import com.university.erp.model.User;
import com.university.erp.repository.InstructorRepository;
import com.university.erp.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private UserService userService;

    public void save(Instructor instructor) {
        userService.save(instructor.getUser());
        instructorRepository.save(instructor);
    }

    public Instructor findById(Long instructorId){
        Optional<Instructor> instructor = instructorRepository.findById(instructorId);
        if(instructor.isPresent()){
            return instructor.get();
        }
        throw new EntityNotFoundException("Instructor not found");
    }
}
