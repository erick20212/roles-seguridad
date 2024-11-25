package com.example.security.entity;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "persona")
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_persona")
    private Long id;

    @Column(name="nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name="apellido", length = 100, nullable = false)
    private String apellido;

    @Column(name="email", length = 100, nullable = false)
    private String email;

    @Column(name="dni", length = 50, nullable = false)
    private String dni;
    
    @Column(name = "telefono", length = 20, nullable = true) // Agrega el atributo si falta
    private String telefono;

    @Column(length = 20, nullable = false)
    private String estado;

    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    @JsonIgnore
    private Usuario usuarios;
}