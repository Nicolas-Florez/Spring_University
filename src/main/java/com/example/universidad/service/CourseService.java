package com.example.universidad.service;

import com.example.universidad.entity.Course;

import java.util.List;

public interface CourseService {
    List<Course> findAll();
    Course findById(Long id);
    Course save(Course course, Long professorId);
    List<Course> findByProfessorName(String professorName);
}
