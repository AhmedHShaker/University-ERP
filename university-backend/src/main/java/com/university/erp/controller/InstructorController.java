package com.university.erp.controller;

import com.university.erp.model.Course;
import com.university.erp.model.Instructor;
import com.university.erp.service.CourseService;
import com.university.erp.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/instructor")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private CourseService courseService;

    @PostMapping("/assignCourse")
    public ResponseEntity<String> assignCourse(@RequestParam Long instructorId, @RequestParam String courseCode) {
        try {
            // Retrieve the instructor and course entities
            Instructor instructor = instructorService.findById(instructorId);
            Course course = courseService.getCourseByCourseCode(courseCode);

            // Assign the course to the instructor
            instructor.addCourse(course);
            instructorService.save(instructor);

            return ResponseEntity.ok("Course assigned to instructor successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to assign course to instructor.");
        }
    }

    @GetMapping
    public ResponseEntity<Instructor> getInstructor(@RequestParam Long instructorId) {
        try {
            // Retrieve the instructor and course entities
            Instructor instructor = instructorService.findById(instructorId);
            return new ResponseEntity<>(instructor, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
