package com.example.security.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "solicitud")
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_estudiante", nullable = false)
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "id_empresa", nullable = true)
    private Empresa empresa;  // Puede ser nulo si el estudiante no seleccionó una empresa preexistente

    @ManyToOne
    @JoinColumn(name = "id_linea_carrera", nullable = true)
    private Linea lineaCarrera;  // Puede ser nulo si no seleccionó una línea de carrera preexistente

    // Datos adicionales de la empresa si es una solicitud para una nueva empresa
    @Column(name = "nombre_empresa", length = 100)
    private String nombreEmpresa;

    @Column(name = "ruc_empresa", length = 20)
    private String rucEmpresa;

    @Column(name = "direccion_empresa", length = 100)
    private String direccionEmpresa;

    @Column(name = "telefono_empresa", length = 20)
    private String telefonoEmpresa;

    @Column(name = "correo_empresa", length = 100)
    private String correoEmpresa;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "estado", length = 20, nullable = false)
    private String estado = "pendiente";  // Estado inicial de la solicitud
}