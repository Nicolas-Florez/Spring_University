package com.example.universidad.repository;

import com.example.universidad.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Query Method: Spring Data crea la consulta automáticamente
    // SELECT * FROM estudiantes WHERE nombre_completo = ?
    List<Student> findByNombre(String nombre);

    // Query Method más complejo
    // SELECT * FROM estudiantes WHERE email LIKE '%@%.com%'
    List<Student> findByEmailContaining(String emailFragment);
}
