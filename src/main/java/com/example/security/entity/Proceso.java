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
@Table(name = "procesos")
public class Proceso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proceso")
    private long id;
    @Column(name = "nombre_proceso", length = 250, nullable = false)
    private String nombre_proceso;
    @Column(name = "tipo_proceso", length = 250, nullable = false)
    private String tipo_proceso;
    @Column(name = "estado_proceso", length = 250, nullable = false)
    private String estado_proceso;
    @Column(name = "estado", length = 30, nullable = false)
    private String estado;

    @OneToMany(mappedBy = "proceso", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Proceso_Requisito> procesoRequisitos;

    @OneToMany(mappedBy = "proceso", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<detalle_ppp> detallePpps;
}
