package com.example.security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "evaluaciones")
public class Evaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evaluacion")
    private long id;

    @Temporal(TemporalType.TIMESTAMP)  // O TemporalType.DATE para solo la fecha
    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "nota")
    private Integer nota;

    @Column(name = "descripcion", length = 250, nullable = false)
    private String descripcion;

    @Column(name = "estado", length = 25, nullable = false)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_ppp", referencedColumnName = "id_ppp", nullable = false)
    private PPP ppp;

    @ManyToOne
    @JoinColumn(name = "id_rubro", referencedColumnName = "id_rubro", nullable = false)
    private Rubro rubro;
}
