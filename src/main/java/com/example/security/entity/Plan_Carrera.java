package com.example.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "plan_carreras")
public class Plan_Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plan_carrera")
    private long id;
    @ManyToOne
    @JoinColumn(name = "id_plan", referencedColumnName = "id_plan", nullable = false)
    private Plan plan;

    // Relación Muchos a Uno con Carrera
    @ManyToOne
    @JoinColumn(name = "id_carrera", referencedColumnName = "id_carrera", nullable = false)
    private Carrera carrera;

    @OneToMany(mappedBy = "plan_carrera", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Matricula> matriculas;

    @OneToMany(mappedBy = "plan_carrera", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Detalle_evaluacion> detalleEvaluacions;

    @Column(name = "estado")
    private String estado;

    @OneToMany(mappedBy = "plan_carrera", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Proceso_Requisito> procesoRequisitos;


    @OneToMany(mappedBy = "plan_carrera", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PPP> ppps;
}
