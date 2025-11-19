package com.example.universidad.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "estudiantes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_completo", length = 100, nullable = false)
    private String nombre;

    @Column(unique = true)
    private String email;

    private LocalDate fechaNacimiento;

    @Transient
    private Integer edad;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "detalle_id")
    private StudentDetail detalle;

    @ManyToMany
    @JoinTable(
            name = "estudiante_curso",
            joinColumns = @JoinColumn(name = "estudiante_id"),
            inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    @JsonIgnore
    private Set<Course> cursos = new HashSet<>();

    public Integer getEdad() {
        if (fechaNacimiento == null) return null;
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    @Override
    public int hashCode() {
        return (id == null) ? 0 : id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student other = (Student) o;
        return id != null && id.equals(other.getId());
    }

    @Override
    public String toString() {
        return "Student{id=" + id + ", nombre='" + nombre + "'}";
    }
}
