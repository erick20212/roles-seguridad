package com.example.security.dto;

import lombok.Data;

@Data
public class EstudianteDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String dni;
    private String codigo;
}
