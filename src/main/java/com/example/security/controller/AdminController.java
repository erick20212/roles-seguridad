package com.example.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.security.entity.Rol;
import com.example.security.entity.Usuario;
import com.example.security.entity.Usuario_Rol;
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

        // Verifica si el usuario ya tiene este rol
        boolean hasRole = usuario.getUsuarioRoles().stream()
                .anyMatch(usuarioRol -> usuarioRol.getRol().equals(rol));
        if (hasRole) {
            return new ResponseEntity<>("El usuario ya tiene este rol", HttpStatus.BAD_REQUEST);
        }

        // Asigna el rol
        Usuario_Rol usuarioRol = new Usuario_Rol();
        usuarioRol.setUsuario(usuario);
        usuarioRol.setRol(rol);
        usuarioRol.setEstadoUsuarioRol("activo"); // Cambia 'a' por "activo" o el valor que desees

        usuario.getUsuarioRoles().add(usuarioRol);
        usuarioRepository.save(usuario);

        return new ResponseEntity<>("Rol asignado exitosamente", HttpStatus.OK);
    }
}
