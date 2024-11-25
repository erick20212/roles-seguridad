package com.example.security.dto;


import lombok.Data;

@Data
public class PersonaUsuarioDTO {
    // Datos de la persona
    private Long idPersona;         // ID de la persona (puede ser nulo si es nuevo)
    private String nombre;          // Nombre de la persona
    private String apellido;        // Apellido de la persona
    private String emailPersona;    // Email de la persona
    private String dni;             // DNI de la persona
    private String estadoPersona;   // Estado de la persona (activo o inactivo)

    // Datos del usuario
    private Long idUsuario;         // ID del usuario (puede ser nulo si es nuevo)
    private String username;        // Nombre de usuario (único)
    private String emailUsuario;    // Email del usuario (único)
    private String password;        // Contraseña del usuario
    private String login;           // Login del usuario
    private String img;             // Imagen del usuario
    private String estadoUsuario;   // Estado del usuario (activo o inactivo)
}