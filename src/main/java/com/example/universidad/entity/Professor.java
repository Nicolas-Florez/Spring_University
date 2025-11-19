package com.example.universidad.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "profesores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String departamento;

    @OneToMany(mappedBy = "profesor", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Course> cursosAsignados;

    @Override
    public int hashCode() {
        return (id == null) ? 0 : id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Professor)) return false;
        Professor other = (Professor) o;
        return id != null && id.equals(other.getId());
    }

    @Override
    public String toString() {
        return "Professor{id=" + id + ", nombre='" + nombre + "'}";
    }
}
