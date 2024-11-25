package com.example.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupervisorDTO {
    private Long id;            // ID del supervisor (para actualizaciones)
    private String nombre;      // Nombre del supervisor
    private String apellido;    // Apellido del supervisor
    private String email;       // Correo electrónico del supervisor
    private String dni;         // Documento de identidad
}