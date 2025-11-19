package com.example.universidad.repository;

import com.example.universidad.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    // Query Method navegando por relaciones
    // Busca cursos por el nombre del profesor
    List<Course> findByProfesorNombre(String nombreProfesor);
}
