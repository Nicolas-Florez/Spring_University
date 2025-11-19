package com.example.universidad.controller;

import com.example.universidad.entity.Professor;
import com.example.universidad.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 1. Indica que esta clase es un Controlador REST
@RequestMapping("/api/profesores") // 2. Define la URL base para este controlador
public class ProfessorController {

    // 3. Inyecta el servicio
    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public List<Professor> getAllProfesores() {
        return professorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professor> getProfessorById(@PathVariable Long id) {
        Professor prof = professorService.findById(id);
        if (prof != null) {
            return ResponseEntity.ok(prof); // Devuelve 200 OK
        }
        return ResponseEntity.notFound().build(); // Devuelve 404 Not Found
    }

    @PostMapping
    public Professor createProfessor(@RequestBody Professor professor) {
        return professorService.save(professor);
    }
}
