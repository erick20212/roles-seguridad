package com.example.security.entity;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "roles")
public class Rol {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "Estado_Rol")
    private String estadoRol;

    public Rol(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Rol_Acceso> rolAcceso;

    // Cambia ManyToMany a OneToMany a través de Usuario_Rol
    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Usuario_Rol> usuarioRol;
}