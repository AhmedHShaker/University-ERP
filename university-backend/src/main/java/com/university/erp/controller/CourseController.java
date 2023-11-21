package com.university.erp.controller;

import com.university.erp.model.Course;
import com.university.erp.model.Student;
import com.university.erp.service.CourseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<List<Course>> getCourses() {
        try {
            List<Course> courses = courseService.getAllCourses();
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{courseCode}")
    public ResponseEntity<Course> getCourseByCourseCode(@PathVariable String courseCode) {
        try {
            Course course = courseService.getCourseByCourseCode(courseCode);

            if (course != null) {
                return new ResponseEntity<>(course, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course createdCourse = courseService.createCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long courseId, @RequestBody Course updatedCourse) {
        Course updated = courseService.updateCourse(courseId, updatedCourse);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.ok("Course deleted successfully");
    }

    // API to get students enrolled in a specific course
    @GetMapping("/{courseId}/students")
    public ResponseEntity<List<Student>> getStudentsEnrolledInCourse(@PathVariable Long courseId) {
        try {
            List<Student> enrolledStudents = courseService.getStudentsEnrolledInCourse(courseId);
            return new ResponseEntity<>(enrolledStudents, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
