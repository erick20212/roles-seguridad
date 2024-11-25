package com.example.security.dto;

import lombok.Data;

@Data
public class PersonaUsuarioDTO {
    // Datos de la persona
    private String nombre;          // Nombre de la persona
    private String apellido;        // Apellido de la persona
    private String emailPersona;    // Email de la persona
    private String dni;             // DNI de la persona

    // Datos del usuario
    private String username;        // Nombre de usuario (único)
    private String login;           // Login del usuario
    private String img = "text.png"; // Imagen por defecto (si lo necesitas)
}
