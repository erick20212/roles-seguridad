package com.example.security.controller;

import com.example.security.dto.PersonaUsuarioDTO;
import com.example.security.serviceImpl.PersonaUsuarioService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/persona-usuario")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class PersonaUsuarioController {

    private final PersonaUsuarioService personaUsuarioService;

    /**
     * Endpoint para crear una persona y un usuario.
     *
     * @param personaUsuarioDTO DTO que contiene los datos de la persona y el usuario.
     * @return ResponseEntity con el resultado de la operación.
     */
    @PostMapping("/crear")
    public ResponseEntity<String> crearPersonaYUsuario(@Valid @RequestBody PersonaUsuarioDTO personaUsuarioDTO) {
        try {
            personaUsuarioService.crearPersonaYUsuario(personaUsuarioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Persona y usuario creados con éxito.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear persona y usuario: " + e.getMessage());
        }
    }
}