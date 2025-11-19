package com.example.universidad.controller;

import com.example.universidad.entity.Course;
import com.example.universidad.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<Course> getAllCursos() {
        return courseService.findAll();
    }

    // Endpoint para crear un curso asignándolo a un profesor
    // ej: POST /api/cursos?profesorId=1
    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course,
                                               @RequestParam Long profesorId) {
        Course nuevoCurso = courseService.save(course, profesorId);
        if (nuevoCurso != null) {
            return ResponseEntity.ok(nuevoCurso);
        }
        return ResponseEntity.badRequest().build(); // 400 Bad Request si el profesorId no existe
    }

    // Endpoint para buscar cursos por nombre de profesor
    // ej: GET /api/cursos/buscar?profesor=Profesor Jirafales
    @GetMapping("/buscar")
    public List<Course> getCursosPorProfesor(@RequestParam String profesor) {
        return courseService.findByProfessorName(profesor);
    }
}
