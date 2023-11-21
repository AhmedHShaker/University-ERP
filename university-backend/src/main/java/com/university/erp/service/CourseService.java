package com.university.erp.service;

import com.university.erp.model.Course;
import com.university.erp.model.Student;
import com.university.erp.repository.CourseRepository;
import com.university.erp.repository.InstructorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    public Course createCourse(Course course) {
        // creates a new course
        return courseRepository.save(course);
    }

    public Course updateCourse(Long courseId, Course updatedCourse) {
        // Retrieve the course by ID
        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));
        // Update course details
        existingCourse.setCourseCode(updatedCourse.getCourseCode());
        existingCourse.setCourseName(updatedCourse.getCourseName());
        existingCourse.setCourseDescription(updatedCourse.getCourseDescription());
        // Update other course properties as needed
        // Save the updated course
        return courseRepository.save(existingCourse);
    }

    public void deleteCourse(Long courseId) {
        // Retrieve the course by ID
        Course courseToDelete = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));
        // Delete the course
        courseRepository.delete(courseToDelete);
    }

    public List<Student> getStudentsEnrolledInCourse(Long courseId) throws EntityNotFoundException {
        Optional<Course> courseOptional = courseRepository.findById(courseId);

        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            return course.getStudents();
        } else {
            throw new EntityNotFoundException("Course not found with ID: " + courseId);
        }
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseByCourseCode(String courseCode) {
        return courseRepository.findByCourseCode(courseCode);
    }
}
