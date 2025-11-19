package com.example.universidad.service;

import com.example.universidad.entity.Course;
import com.example.universidad.entity.Professor;
import com.example.universidad.repository.CourseRepository;
import com.example.universidad.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Course findById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    public Course save(Course course, Long professorId) {
        // Buscamos al profesor por su ID
        Professor professor = professorRepository.findById(professorId).orElse(null);
        if (professor != null) {
            // Asignamos el profesor al curso (lado dueño de la relación N:1)
            course.setProfesor(professor);
            return courseRepository.save(course);
        }
        // Manejar el caso de profesor no encontrado (idealmente con una excepción)
        return null;
    }

    @Override
    public List<Course> findByProfessorName(String professorName) {
        // Reutilizamos el Query Method del repositorio
        return courseRepository.findByProfesorNombre(professorName);
    }
}
