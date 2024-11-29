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
	@GeneratedValue(strategy = GenerationType.IDENTITY)  // Asegúrate de que tu base de datos soporte esta estrategia
	@Column(name = "id_usuario")
	private Long id;

	@Column(nullable = false, unique = true)
	private String username;  // Asegúrate de que username sea único

	@Column(nullable = false, unique = true)
	private String email;  // Asegúrate de que email sea único

	@Column(nullable = false)
	private String password;  // Asegúrate de encriptar la contraseña

	@Column(name = "login", nullable = false)
	private String login;  // Este campo se utiliza como login

	@Column(name = "img", length = 500, nullable = false)
	private String img;  // Ruta o nombre de la imagen del usuario

	@Column(length = 20, nullable = false)
	private String estado;  // Estado del usuario (activo, inactivo, etc.)

	// Relación OneToMany con Usuario_Rol
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JsonIgnore  // No se serializa la lista de roles en JSON
	private List<Usuario_Rol> usuarioRoles;

	// Relación OneToOne con Persona
	@OneToOne
	@JoinColumn(name = "id_persona", referencedColumnName = "id_persona", nullable = false)
	private Persona persona;  // Cada usuario tiene una persona asociada

}
