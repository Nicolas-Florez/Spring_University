package com.example.universidad.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalles_estudiante")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String direccion;
    private String telefono;

    // 10. Relación 1:1 (Bidireccional - Lado Inverso)
    // "mappedBy" indica que la otra entidad (Student)
    // es la dueña de la relación (ella tiene la JoinColumn).
    @OneToOne(mappedBy = "detalle")
    @JsonIgnore
    private Student estudiante;
}
