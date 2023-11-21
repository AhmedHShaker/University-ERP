package com.university.erp.service;

import com.university.erp.model.Course;
import com.university.erp.model.Instructor;
import com.university.erp.model.Student;
import com.university.erp.repository.CourseRepository;
import com.university.erp.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserService userService;

    public void enrollInCourse(Long userId, String courseCode) {
        // Check if the student and course exist
        Student student = studentRepository.findByUserUserId(userId);
        if(student == null){
            throw new EntityNotFoundException("Student not found with id: " + userId);
        }

        Course course = courseRepository.findByCourseCode(courseCode);
        if(course == null) {
            throw new EntityNotFoundException("Course not found with id: " + courseCode);
        }

        // Check if the course is already enrolled by the student
        if (student.getCourses().contains(course)) {
            throw new IllegalStateException("Student is already enrolled in this course.");
        }

        // Check if the course has reached its maximum capacity
        if (course.getStudents().size() >= course.getMaxCapacity()) {
            throw new IllegalStateException("Course has reached its maximum capacity.");
        }

        // Enroll the student in the course
        student.addCourse(course);
        studentRepository.save(student);
    }

    public void withdrawFromCourse(Long userId, String courseCode) {
        // Check if the student and course exist
        Student student = studentRepository.findByUserUserId(userId);
        if(student == null){
            throw new EntityNotFoundException("Student not found with id: " + userId);
        }

        Course course = courseRepository.findByCourseCode(courseCode);
        if(course == null) {
            throw new EntityNotFoundException("Course not found with id: " + courseCode);
        }

        // Check if the student is enrolled in the course
        if (!student.getCourses().contains(course)) {
            throw new IllegalStateException("Student is not enrolled in this course.");
        }

        // Withdraw the student from the course
        student.removeCourse(course);
        studentRepository.save(student);
    }

    public List<Course> getCoursesEnrolledIn(Long userId) {
        // Check if the student exists
        Student student = studentRepository.findByUserUserId(userId);
        // Get the list of courses enrolled in by the student
        return student.getCourses();
    }

    public void save(Student student) {
        userService.save(student.getUser());
        studentRepository.save(student);
    }

    public Student findByUserId(Long userId) {
        return studentRepository.findByUserUserId(userId);
    }

    public boolean isStudentEnrolled(Long userId, String courseCode) {
        Student student = studentRepository.findByUserUserId(userId);
        Course course = courseRepository.findByCourseCode(courseCode);

        // If either the student or course is not found, return false
        if (student == null || course == null) {
            return false;
        }

        // Check if the student is in the list of students for the course
        return course.getStudents().contains(student);
    }
}