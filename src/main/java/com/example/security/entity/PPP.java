package com.example.security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.sound.sampled.Line;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ppp")
public class PPP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ppp")
    private long id;

    @Temporal(TemporalType.TIMESTAMP)  // O TemporalType.DATE para solo la fecha
    @Column(name = "fecha_inicio")
    private Date fecha_inicio;

    @Temporal(TemporalType.TIMESTAMP)  // O TemporalType.DATE para solo la fecha
    @Column(name = "fecha_final")
    private Date fecha_final;

    @Column(name = "horas")
    private Double horas;

    @Column(name = "promedio")
    private Double promedio;

    @Column(name = "estado", length = 25, nullable = false)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_plan_carrera", referencedColumnName = "id_plan_carrera", nullable = false)
    private Plan_Carrera plan_carrera;

    @ManyToOne
    @JoinColumn(name = "id_empresa", referencedColumnName = "id_empresa", nullable = false)
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "id_linea", referencedColumnName = "id_linea", nullable = false)
    private Linea linea;

    @ManyToOne
    @JoinColumn(name = "id_matricula", referencedColumnName = "id_matricula", nullable = false)
    private Matricula matricula;
}
