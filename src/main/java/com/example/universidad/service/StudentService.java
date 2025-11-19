package com.example.universidad.service;

import com.example.universidad.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> findAll();
    Student findById(Long id);
    Student save(Student student);
    Student assignCourse(Long studentId, Long courseId);
}
