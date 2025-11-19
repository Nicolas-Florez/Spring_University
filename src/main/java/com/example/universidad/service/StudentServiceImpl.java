package com.example.universidad.service;

import com.example.universidad.entity.Course;
import com.example.universidad.entity.Student;
import com.example.universidad.repository.CourseRepository;
import com.example.universidad.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public Student save(Student student) {
        // Si el estudiante trae un detalle, establecemos la relación bidireccional
        // La relación se guarda en cascada (CascadeType.ALL en la entidad Student)
        if (student.getDetalle() != null) {
            student.getDetalle().setEstudiante(student);
        }
        return studentRepository.save(student);
    }

    @Override
    public Student assignCourse(Long studentId, Long courseId) {
        // Buscamos ambas entidades
        Student student = studentRepository.findById(studentId).orElse(null);
        Course course = courseRepository.findById(courseId).orElse(null);

        if (student != null && course != null) {
            // Añadimos el curso al set del estudiante
            student.getCursos().add(course);
            // Guardamos el estudiante (lado dueño de la relación N:N)
            return studentRepository.save(student);
        }
        // Manejar caso de no encontrar (idealmente con excepciones)
        return null;
    }
}
