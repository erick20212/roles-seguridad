package com.example.security.controller;

import com.example.security.dto.AuthResponseDto;
import com.example.security.dto.LoginDto;
import com.example.security.dto.RegisterDto;
import com.example.security.entity.Usuario;
import com.example.security.entity.Rol;
import com.example.security.repository.UsuarioRepository;
import com.example.security.repository.RolRepository;
import com.example.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto); // Obtener el token desde AuthService

        // Buscar usuario por nombre de usuario
        Usuario usuario = usuarioRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Obtener el primer rol del usuario (asumiendo que tiene al menos uno)
        String role = usuario.getRoles().stream().findFirst().map(Rol::getName).orElse("USER");

        // Configurar la respuesta de autenticación
        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setAccessToken(token); // Token
        authResponseDto.setWelcomeMessage("Hola " + role); // Mensaje personalizado
        authResponseDto.setUsername(usuario.getUsername()); // Nombre de usuario
        authResponseDto.setRole(role); // Rol

        // Retornar la respuesta con el token, mensaje, nombre y rol
        return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDto registerDto) {
        Usuario usuario = new Usuario();
        usuario.setName(registerDto.getName());
        usuario.setUsername(registerDto.getUsername());
        usuario.setEmail(registerDto.getEmail());
        usuario.setPassword(passwordEncoder.encode(registerDto.getPassword())); // Codificar la contraseña

        Set<Rol> roles = new HashSet<>();
        Rol userRole = rolRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Error: Rol USER no encontrado"));
        roles.add(userRole);
        usuario.setRoles(roles); // Asignar rol

        usuarioRepository.save(usuario); // Guardar usuario
        return new ResponseEntity<>("Usuario registrado exitosamente", HttpStatus.CREATED);
    }
}
