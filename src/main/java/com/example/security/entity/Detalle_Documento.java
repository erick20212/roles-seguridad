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
@Table(name = "detalle_documentos")
public class Detalle_Documento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_documento")
    private long id;
    @Column(name = "nombre_documento", length = 250)
    private String nombre_documento;

    @Temporal(TemporalType.TIMESTAMP)  // O TemporalType.DATE para solo la fecha
    @Column(name = "fecha_documento")
    private Date fecha_documento;

    @Column(name = "estado", length = 40, nullable = false)
    private String estado;
    
    
}

