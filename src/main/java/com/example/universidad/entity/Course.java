package com.example.universidad.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "cursos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreCurso;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor profesor;

    @ManyToMany(mappedBy = "cursos")
    @JsonIgnore
    private Set<Student> estudiantes;

    // --- equals & hashCode SOLO con ID ---

    @Override
    public int hashCode() {
        return (id == null) ? 0 : id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course other = (Course) o;
        return id != null && id.equals(other.getId());
    }

    @Override
    public String toString() {
        return "Course{" + "id=" + id + ", nombreCurso='" + nombreCurso + "'}";
    }
}
