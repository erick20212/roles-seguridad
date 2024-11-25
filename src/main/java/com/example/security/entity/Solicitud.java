package com.example.security.entity;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
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

	    // Relación con Estudiante
	    @ManyToOne
	    @JoinColumn(name = "id_estudiante", nullable = false)
	    private Estudiante estudiante;

	    // Relación con Empresa
	    @ManyToOne
	    @JoinColumn(name = "id_empresa", nullable = true)
	    private Empresa empresa;

	    // Relación con Línea de Carrera
	    @ManyToOne
	    @JoinColumn(name = "id_linea", nullable = true)
	    private Linea lineaCarrera;

	    // Estado de la solicitud (enum para consistencia)
	    @Column(name = "estado", length = 20, nullable = false)
	    private String estado = "PENDIENTE"; // Ejemplo de estado inicial

	    // Fecha de creación
	    @Column(name = "fecha_creacion", nullable = false)
	    private LocalDateTime fechaCreacion;

	    @PrePersist
	    protected void onCreate() {
	        this.fechaCreacion = LocalDateTime.now();
	    }
}