package com.example.security.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Data
@Table(name = "usuarios")
public class Usuario {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id_usuario")
	    private Long id;

	    @Column(nullable = false, unique = true)
	    private String username;

	    @Column(nullable = false, unique = true)
	    private String email;

	    @Column(nullable = false)
	    private String password;
	    
	    @Column(name="login",nullable = false)
	    private String login;
	    
	    @Column(name="img",length = 500, nullable = false)
	    private String img;
	    
	    @Column(length = 20, nullable = false)
	    private String estado;
	    
	   

	    // Cambia ManyToMany a OneToMany a través de Usuario_Rol
	    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	    @JsonIgnore
	    private List<Usuario_Rol> usuarioRoles;
	 

	    @ManyToOne
	    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona", nullable = false)
	    private Persona persona;
	}