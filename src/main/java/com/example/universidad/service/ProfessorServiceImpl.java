package com.example.universidad.service;

import com.example.universidad.entity.Professor;
import com.example.universidad.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // 1. Marcamos esta clase como un Servicio de Spring
public class ProfessorServiceImpl implements ProfessorService {

    // 2. Inyectamos el Repositorio para acceder a la BD
    @Autowired
    private ProfessorRepository professorRepository;

    @Override
    public List<Professor> findAll() {
        return professorRepository.findAll();
    }

    @Override
    public Professor findById(Long id) {
        // findById devuelve un Optional, usamos orElse(null) si no lo encuentra
        return professorRepository.findById(id).orElse(null);
    }

    @Override
    public Professor save(Professor professor) {
        return professorRepository.save(professor);
    }

    @Override
    public void deleteById(Long id) {
        professorRepository.deleteById(id);
    }
}
