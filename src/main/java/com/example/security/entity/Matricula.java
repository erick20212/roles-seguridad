package com.example.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "matriculas")
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_matricula")
    private long id;

    @Temporal(TemporalType.DATE)  // Usamos TemporalType.DATE para solo la fecha (sin la hora)
    @Column(name = "fecha_matricula")
    private Date fecha_matricula;

    @Column(name = "estado")
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_estudiante", referencedColumnName = "id_estudiante", nullable = false)
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "id_plan_carrera", referencedColumnName = "id_plan_carrera", nullable = false)
    private Plan_Carrera plan_carrera;

    @OneToMany(mappedBy = "matricula", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PPP> ppps;
}
