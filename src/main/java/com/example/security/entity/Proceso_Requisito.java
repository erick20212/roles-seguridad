package com.example.security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "proceso_requisitos")
public class Proceso_Requisito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proceso_requisito")
    private long id;
    @Column(name = "responsable", length = 25, nullable = false)
    private String responsable;
    @Column(name = "estado", length = 25, nullable = false)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_proceso", referencedColumnName = "id_proceso", nullable = false)
    private Proceso proceso;

    @ManyToOne
    @JoinColumn(name = "id_requisito", referencedColumnName = "id_requisito", nullable = false)
    private Requisito requisito;

    @ManyToOne
    @JoinColumn(name = "id_plan_carrera", referencedColumnName = "id_plan_carrera", nullable = false)
    private Plan_Carrera plan_carrera;

}
