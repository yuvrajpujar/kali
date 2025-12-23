package com.kali;

import com.kali.entity.Lesson;
import com.kali.repository.LessonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LessonControllerIntegrationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private LessonRepository lessonRepository;

    @BeforeEach
    void setUp() {
        lessonRepository.deleteAll();
        Lesson lesson = new Lesson();
        lesson.setTitle("Welcome");
        lesson.setContentType("video");
        lesson.setContentUrl("http://example.com/welcome.mp4");
        lesson.setBodyContent("Welcome to Java");
        lesson.setIsPreview(true);
        lesson.setDurationSeconds(300);
        lesson.setOrderIndex(1);
        lessonRepository.save(lesson);
    }

    @Test
    void testGetAllLessons() {
        ResponseEntity<Lesson[]> response = restTemplate.getForEntity("/api/lessons", Lesson[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThanOrEqualTo(1);
    }

    @Test
    void testCreateLesson() {
        Lesson lesson = new Lesson();
        lesson.setTitle("Variables");
        lesson.setContentType("text");
        lesson.setBodyContent("Java variables explained");
        lesson.setIsPreview(false);
        lesson.setDurationSeconds(0);
        lesson.setOrderIndex(2);
        ResponseEntity<Lesson> response = restTemplate.postForEntity("/api/lessons", lesson, Lesson.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getLessonId()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo("Variables");
    }

    @Test
    void testGetLessonById() {
        Lesson lesson = new Lesson();
        lesson.setTitle("Welcome");
        lesson.setContentType("video");
        lesson.setBodyContent("Welcome to Java");
        lesson.setIsPreview(true);
        lesson.setDurationSeconds(300);
        lesson.setOrderIndex(1);
        Lesson saved = lessonRepository.save(lesson);
        ResponseEntity<Lesson> response = restTemplate.getForEntity("/api/lessons/" + saved.getLessonId(), Lesson.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo("Welcome");
    }

    @Test
    void testUpdateLesson() {
        Lesson lesson = new Lesson();
        lesson.setTitle("Old Title");
        lesson.setContentType("text");
        lesson.setBodyContent("Old content");
        lesson.setIsPreview(false);
        lesson.setDurationSeconds(0);
        lesson.setOrderIndex(1);
        Lesson saved = lessonRepository.save(lesson);
        Lesson update = new Lesson();
        update.setTitle("New Title");
        update.setContentType("video");
        update.setBodyContent("New content");
        update.setIsPreview(true);
        update.setDurationSeconds(100);
        update.setOrderIndex(2);
        ResponseEntity<Lesson> response = restTemplate.exchange("/api/lessons/" + saved.getLessonId(), org.springframework.http.HttpMethod.PUT, new org.springframework.http.HttpEntity<>(update), Lesson.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo("New Title");
    }

    @Test
    void testDeleteLesson() {
        Lesson lesson = new Lesson();
        lesson.setTitle("To Delete");
        lesson.setContentType("text");
        lesson.setBodyContent("To be deleted");
        lesson.setIsPreview(false);
        lesson.setDurationSeconds(0);
        lesson.setOrderIndex(3);
        Lesson saved = lessonRepository.save(lesson);
        ResponseEntity<Void> response = restTemplate.exchange("/api/lessons/" + saved.getLessonId(), org.springframework.http.HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(lessonRepository.findById(saved.getLessonId())).isEmpty();
    }
}
