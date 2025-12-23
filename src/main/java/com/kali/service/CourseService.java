package com.kali.service;

import com.kali.entity.Course;
import com.kali.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course courseDetails) {
        return courseRepository.findById(id)
                .map(course -> {
                    course.setTitle(courseDetails.getTitle());
                    course.setSlug(courseDetails.getSlug());
                    course.setDescription(courseDetails.getDescription());
                    course.setCategory(courseDetails.getCategory());
                    course.setInstructorId(courseDetails.getInstructorId());
                    course.setPrice(courseDetails.getPrice());
                    course.setStatus(courseDetails.getStatus());
                    course.setMetadata(courseDetails.getMetadata());
                    course.setUpdatedAt(courseDetails.getUpdatedAt());
                    return courseRepository.save(course);
                })
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
