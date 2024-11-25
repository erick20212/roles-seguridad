package com.example.security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "detalle_ppps")
public class detalle_ppp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_ppp")
    private long id;
    @Column(name = "responsable", length = 250, nullable = false)
    private String responsable;
    @Column(name = "estado", length = 25, nullable = false)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_ppp", referencedColumnName = "id_ppp", nullable = false)
    private PPP ppp;

    @ManyToOne
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona", nullable = false)
    private Persona persona;

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
