package com.example.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.security.entity.Rol;
import com.example.security.entity.Usuario;
import com.example.security.repository.RolRepository;
import com.example.security.repository.UsuarioRepository;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins ="http://localhost:4200")
public class AdminController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    // Endpoint para asignar roles. Solo accesible para ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/assign-role")
    public ResponseEntity<String> assignRoleToUser(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String roleName = payload.get("roleName");

        if (username == null || roleName == null) {
            return new ResponseEntity<>("El nombre de usuario y el rol son obligatorios", HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Rol rol = rolRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        if (usuario.getRoles().contains(rol)) {
            return new ResponseEntity<>("El usuario ya tiene este rol", HttpStatus.BAD_REQUEST);
        }

        usuario.getRoles().add(rol);
        usuarioRepository.save(usuario);

        return new ResponseEntity<>("Rol asignado exitosamente", HttpStatus.OK);
    }

    // Método opcional para crear roles adicionales desde el controlador de administración
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-role")
    public ResponseEntity<String> createRole(@RequestBody Map<String, String> payload) {
        String roleName = payload.get("roleName");

        if (roleName == null) {
            return new ResponseEntity<>("El nombre del rol es obligatorio", HttpStatus.BAD_REQUEST);
        }

        if (rolRepository.findByName(roleName).isPresent()) {
            return new ResponseEntity<>("El rol ya existe", HttpStatus.BAD_REQUEST);
        }

        Rol rol = new Rol(roleName);
        rolRepository.save(rol);

        return new ResponseEntity<>("Rol creado exitosamente", HttpStatus.CREATED);
    }
}
