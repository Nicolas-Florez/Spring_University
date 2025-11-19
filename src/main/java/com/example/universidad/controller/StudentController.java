package com.example.universidad.controller;

import com.example.universidad.entity.Student;
import com.example.universidad.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> getAllEstudiantes() {
        return studentService.findAll();
    }

    // POST /api/estudiantes
    // En el JSON body, puedes incluir el objeto "detalle" anidado
    // { "nombre": "Ana Torres", "email": "ana@mail.com", "detalle": { "direccion": "..." } }
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.save(student);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student stud = studentService.findById(id);
        if (stud != null) {
            // Probamos el campo @Transient
            System.out.println("Edad calculada: " + stud.getEdad());
            return ResponseEntity.ok(stud);
        }
        return ResponseEntity.notFound().build();
    }

    // Endpoint para la relación N:N
    // PUT /api/estudiantes/1/cursos/3 (Asigna curso 3 al estudiante 1)
    @PutMapping("/{studentId}/cursos/{courseId}")
    public ResponseEntity<Student> assignCourse(@PathVariable Long studentId,
                                                @PathVariable Long courseId) {
        Student student = studentService.assignCourse(studentId, courseId);
        if (student != null) {
            return ResponseEntity.ok(student);
        }
        return ResponseEntity.notFound().build();
    }
}
