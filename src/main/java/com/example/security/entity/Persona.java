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

    @Column(name="DNI", length = 50, nullable = false)
    private String dni;

    @Column(length = 20, nullable = false)
    private String estado;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Usuario> usuarios;
}