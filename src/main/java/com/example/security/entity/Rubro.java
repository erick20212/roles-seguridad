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
@Table(name = "rubros")
public class Rubro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rubro")
    private long id;
    @Column(name = "nombre", length = 250, nullable = false)
    private String nombre;
    @Column(name = "estado", length = 25, nullable = false)
    private String estado;

    @OneToMany(mappedBy = "rubro", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Detalle_evaluacion> detalleEvaluacions;

    @OneToMany(mappedBy = "rubro", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Evaluacion> evaluacions;

}
