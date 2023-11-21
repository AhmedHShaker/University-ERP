package com.university.erp.controller;

import com.university.erp.model.Admin;
import com.university.erp.model.Instructor;
import com.university.erp.model.Student;
import com.university.erp.model.User;
import com.university.erp.service.AdminService;
import com.university.erp.service.InstructorService;
import com.university.erp.service.StudentService;
import com.university.erp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
//@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private AdminService adminService;

    @PostMapping("/addInstructor")
    public ResponseEntity<String> addInstructor(@RequestBody Instructor instructor) {
        try {
            instructorService.save(instructor);
            return ResponseEntity.ok("Instructor added successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Failed to add Instructor.");
        }
    }

    @PostMapping("/addStudent")
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        try {
            studentService.save(student);
            return ResponseEntity.ok("Student added successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Failed to add Student.");
        }
    }

    @PostMapping("/addAdmin")
    public ResponseEntity<String> addAdmin(@RequestBody Admin admin) {
        try {
            adminService.save(admin);
            return ResponseEntity.ok("Admin added successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Failed to add Admin.");
        }
    }


}
