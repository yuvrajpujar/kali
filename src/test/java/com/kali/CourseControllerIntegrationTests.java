package com.kali;

import com.kali.entity.Course;
import com.kali.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CourseControllerIntegrationTests {
        @Autowired
        private com.kali.repository.LessonRepository lessonRepository;
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private com.kali.repository.ModuleRepository moduleRepository;

    @BeforeEach
    void setUp() {
        // Delete lessons before modules and courses to avoid FK constraint errors
        lessonRepository.deleteAll();
        moduleRepository.deleteAll();
        courseRepository.deleteAll();
        Course course = new Course();
        course.setTitle("Java Basics Test");
        course.setSlug("java-basics-test");
        course.setDescription("Intro to Java Test");
        course.setInstructorId(2L);
        course.setPrice(new java.math.BigDecimal("49.99"));
        course.setStatus("published");
        course.setMetadata("{\"level\": \"beginner\"}");
        course.setUpdatedAt(java.time.LocalDateTime.now());
        courseRepository.save(course);
    }

    // @Test
    // void testGetAllCourses() {
    //     ResponseEntity<Course[]> response = restTemplate.getForEntity("/api/courses", Course[].class);
    //     assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    //     assertThat(response.getBody()).isNotNull();
    //     assertThat(response.getBody().length).isGreaterThanOrEqualTo(1);
    // }

    // @Test
    // void testCreateCourse() {
    //     Course course = new Course();
    //     course.setTitle("Spring Boot Fundamentals");
    //     course.setSlug("spring-boot-fundamentals");
    //     course.setDescription("Spring Boot course");
    //     course.setInstructorId(2L);
    //     course.setPrice(new java.math.BigDecimal("59.99"));
    //     course.setStatus("draft");
    //     course.setMetadata("{\"level\": \"intermediate\"}");
    //     course.setUpdatedAt(java.time.LocalDateTime.now());
    //     ResponseEntity<Course> response = restTemplate.postForEntity("/api/courses", course, Course.class);
    //     assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    //     assertThat(response.getBody()).isNotNull();
    //     assertThat(response.getBody().getCourseId()).isNotNull();
    //     assertThat(response.getBody().getTitle()).isEqualTo("Spring Boot Fundamentals");
    // }

    // @Test
    // void testGetCourseById() {
    //     Course course = new Course();
    //     course.setTitle("Java Basics Test");
    //     String uniqueSlug = "java-basics-test-" + System.currentTimeMillis();
    //     course.setSlug(uniqueSlug);
    //     course.setDescription("Intro to Java Test");
    //     course.setInstructorId(2L);
    //     course.setPrice(new java.math.BigDecimal("49.99"));
    //     course.setStatus("published");
    //     course.setMetadata("{\"level\": \"beginner\"}");
    //     course.setUpdatedAt(java.time.LocalDateTime.now());
    //     Course saved = courseRepository.save(course);
    //     ResponseEntity<Course> response = restTemplate.getForEntity("/api/courses/" + saved.getCourseId(), Course.class);
    //     assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    //     assertThat(response.getBody()).isNotNull();
    //     assertThat(response.getBody().getTitle()).isEqualTo("Java Basics Test");
    // }

    // @Test
    // void testUpdateCourse() {
    //     Course course = new Course();
    //     course.setTitle("Old Title");
    //     course.setSlug("old-title");
    //     course.setDescription("Old description");
    //     course.setInstructorId(2L);
    //     course.setPrice(new java.math.BigDecimal("10.00"));
    //     course.setStatus("draft");
    //     course.setMetadata("{}");
    //     course.setUpdatedAt(java.time.LocalDateTime.now());
    //     Course saved = courseRepository.save(course);
    //     Course update = new Course();
    //     update.setTitle("New Title");
    //     update.setSlug("new-title");
    //     update.setDescription("New description");
    //     update.setInstructorId(2L);
    //     update.setPrice(new java.math.BigDecimal("20.00"));
    //     update.setStatus("published");
    //     update.setMetadata("{}");
    //     update.setUpdatedAt(java.time.LocalDateTime.now());
    //     ResponseEntity<Course> response = restTemplate.exchange("/api/courses/" + saved.getCourseId(), org.springframework.http.HttpMethod.PUT, new org.springframework.http.HttpEntity<>(update), Course.class);
    //     assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    //     assertThat(response.getBody()).isNotNull();
    //     assertThat(response.getBody().getTitle()).isEqualTo("New Title");
    // }

    // @Test
    // void testDeleteCourse() {
    //     Course course = new Course();
    //     course.setTitle("To Delete");
    //     course.setSlug("to-delete");
    //     course.setDescription("To be deleted");
    //     course.setInstructorId(2L);
    //     course.setPrice(new java.math.BigDecimal("0.00"));
    //     course.setStatus("draft");
    //     course.setMetadata("{}");
    //     course.setUpdatedAt(java.time.LocalDateTime.now());
    //     Course saved = courseRepository.save(course);
    //     ResponseEntity<Void> response = restTemplate.exchange("/api/courses/" + saved.getCourseId(), org.springframework.http.HttpMethod.DELETE, null, Void.class);
    //     assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    //     assertThat(courseRepository.findById(saved.getCourseId())).isEmpty();
    // }
}
