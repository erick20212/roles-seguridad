package com.example.security.controller;
import com.example.security.dto.SupervisorDTO;
import com.example.security.entity.Persona;
import com.example.security.entity.Usuario;
import com.example.security.entity.Usuario_Rol;
import com.example.security.repository.UsuarioRepository;
import com.example.security.repository.PersonaRepository;
import com.example.security.repository.Usuario_Rol_Repository;
import com.example.security.service.SupervisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/supervisores")
public class SupervisorController {

    @Autowired
    private Usuario_Rol_Repository usuarioRolRepository;  // Repositorio de Usuario_Rol

    @Autowired
    private UsuarioRepository usuarioRepository;  // Repositorio de Usuario

    @Autowired
    private PersonaRepository personaRepository;  // Repositorio de Persona

    @Autowired
    private SupervisorService supervisorService;

    // Obtener supervisores (Usuarios con rol de supervisor)
    @GetMapping("/listar")
    public List<Persona> listarSupervisores() {
        // 1. Buscar los usuarios que tienen el rol de supervisor (id_rol = 4)
        List<Usuario_Rol> usuarioRoles = usuarioRolRepository.findByRolId(28L);  // Busca por el id_rol del supervisor

        // 2. Obtener los usuarios y sus personas asociadas
        List<Persona> supervisores = usuarioRoles.stream()
                .map(usuarioRol -> {
                    // Obtén el usuario asociado al rol
                    Usuario usuario = usuarioRol.getUsuario();
                    // Devuelve la persona asociada al usuario
                    return usuario.getPersona();
                })
                .collect(Collectors.toList());

        return supervisores;
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarSupervisor(
            @PathVariable Long id,
            @RequestBody SupervisorDTO supervisorDTO
    ) {
        supervisorDTO.setId(id); // Asegura que el ID esté en el DTO
        try {
            supervisorService.actualizarSupervisor(id, supervisorDTO);
            return ResponseEntity.ok("Supervisor actualizado exitosamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Error al actualizar supervisor: " + e.getMessage());
        }
    }


    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<String> borrarSupervisor(@PathVariable Long id) {
        try {
            // Verificar si la persona está asociada a un supervisor (rol con id_rol = 4)
            List<Usuario_Rol> usuarioRoles = usuarioRolRepository.findByRolId(28L);

            // Buscar la relación Usuario_Rol asociada a la persona con el ID dado
            Usuario_Rol usuarioRol = usuarioRoles.stream()
                    .filter(ur -> ur.getUsuario().getPersona().getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No se encontró un supervisor con el ID proporcionado"));

            // Eliminar la relación en Usuario_Rol
            usuarioRolRepository.delete(usuarioRol);  // Elimina la relación del rol con el usuario

            // Eliminar la persona asociada
            Persona persona = usuarioRol.getUsuario().getPersona();
            personaRepository.delete(persona);  // Elimina la persona de la base de datos

            // Devolver un mensaje indicando que la eliminación fue exitosa
            return ResponseEntity.ok("Supervisor con ID " + id + " ha sido eliminado exitosamente.");
        } catch (RuntimeException e) {
            // Si ocurre una excepción (como supervisor no encontrado), respondemos con un mensaje de error
            return ResponseEntity.status(400).body("Error al eliminar supervisor: " + e.getMessage());
        }
    }
}