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
@Table(name = "requisitos")
public class Requisito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_requisito")
    private long id;
    @Column(name = "nombre_requisito", length = 250, nullable = false)
    private String nombre_requisito;
    @Column(name = "tipo", length = 250, nullable = false)
    private String tipo;
    @Column(name = "estado", length = 250, nullable = false)
    private String estado;

    @OneToMany(mappedBy = "requisito", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Proceso_Requisito> procesoRequisitos;

    @OneToMany(mappedBy = "requisito", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<detalle_ppp> detallePpps;
}
