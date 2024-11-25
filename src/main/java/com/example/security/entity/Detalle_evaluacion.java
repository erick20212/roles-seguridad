package com.example.security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "detalle_evaluaiones")
public class Detalle_evaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_evaluacion")
    private long id;
    @Column(name = "peso")
    private Integer peso;

    @ManyToOne
    @JoinColumn(name = "id_rubro", referencedColumnName = "id_rubro", nullable = false)
    private Rubro rubro;

    @ManyToOne
    @JoinColumn(name = "id_plan_carrera", referencedColumnName = "id_plan_carrera", nullable = false)
    private Plan_Carrera plan_carrera;
}
