package com.kali.service;

import com.kali.entity.Lesson;
import com.kali.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonService {
    private final LessonRepository lessonRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    public Optional<Lesson> getLessonById(Long id) {
        return lessonRepository.findById(id);
    }

    public Lesson createLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    public Lesson updateLesson(Long id, Lesson lessonDetails) {
        return lessonRepository.findById(id)
                .map(lesson -> {
                    lesson.setTitle(lessonDetails.getTitle());
                    lesson.setModule(lessonDetails.getModule());
                    lesson.setContentType(lessonDetails.getContentType());
                    lesson.setContentUrl(lessonDetails.getContentUrl());
                    lesson.setBodyContent(lessonDetails.getBodyContent());
                    lesson.setIsPreview(lessonDetails.getIsPreview());
                    lesson.setDurationSeconds(lessonDetails.getDurationSeconds());
                    lesson.setOrderIndex(lessonDetails.getOrderIndex());
                    return lessonRepository.save(lesson);
                })
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
    }

    public void deleteLesson(Long id) {
        lessonRepository.deleteById(id);
    }
}
