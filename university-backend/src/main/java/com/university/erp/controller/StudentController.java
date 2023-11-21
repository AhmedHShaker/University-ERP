package com.university.erp.controller;

import com.university.erp.model.Course;
import com.university.erp.model.Student;
import com.university.erp.service.SecurityService;
import com.university.erp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private SecurityService securityService;

    @PostMapping("/{userId}/enroll/{courseCode}")
    @PreAuthorize("@securityService.isStudentOwner(#userId)")
    public ResponseEntity<String> enrollInCourse(
            @PathVariable Long userId,
            @PathVariable String courseCode) {

        try {
            studentService.enrollInCourse(userId, courseCode);
            return ResponseEntity.ok("Enrolled in the course successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/{userId}/withdraw/{courseCode}")
    @PreAuthorize("@securityService.isStudentOwner(#userId)")
    public ResponseEntity<String> withdrawFromCourse(
            @PathVariable Long userId,
            @PathVariable String courseCode) {

        try {
            studentService.withdrawFromCourse(userId, courseCode);
            return ResponseEntity.ok("Withdrawn from the course successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/{userId}/enrollment/{courseCode}")
    @PreAuthorize("@securityService.isStudentOwner(#userId)")
    public ResponseEntity<Boolean> checkEnrollmentStatus(
            @PathVariable Long userId,
            @PathVariable String courseCode) {

        try {
            boolean isEnrolled = studentService.isStudentEnrolled(userId, courseCode);

            return ResponseEntity.ok(isEnrolled);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(false);
        }
    }

    @GetMapping("/{userId}/courses")
    @PreAuthorize("@securityService.isStudentOwner(#userId)")
    public ResponseEntity<List<Course>> getCoursesEnrolledIn(@PathVariable Long userId) {

        try {
            List<Course> enrolledCourses = studentService.getCoursesEnrolledIn(userId);
            return ResponseEntity.ok(enrolledCourses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }
    @GetMapping("/{userId}")
    @PreAuthorize("@securityService.isStudentOwner(#userId)")
    public ResponseEntity<Student> getStudentByUserId(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(studentService.findByUserId(userId));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}