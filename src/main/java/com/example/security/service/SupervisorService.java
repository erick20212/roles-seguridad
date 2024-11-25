package com.example.security.service;

import com.example.security.dto.SupervisorDTO;
import com.example.security.entity.Persona;
import com.example.security.entity.Usuario;
import com.example.security.entity.Usuario_Rol;
import com.example.security.repository.PersonaRepository;
import com.example.security.repository.UsuarioRepository;
import com.example.security.repository.Usuario_Rol_Repository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class SupervisorService {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private Usuario_Rol_Repository usuarioRolRepository;

    @Autowired
    private PasswordEncoder encode;

    /**
     * Actualiza los datos de un supervisor (Persona y Usuario asociados).
     *
     * @param id ID del supervisor a actualizar.
     * @param supervisorDTO Datos a actualizar.
     */
    @Transactional
    public void actualizarSupervisor(Long id, SupervisorDTO supervisorDTO) {
        // Verificar que el ID no sea nulo
        if (id == null) {
            throw new RuntimeException("El ID del supervisor no puede ser nulo.");
        }

        // Buscar la relación Usuario_Rol asociada al supervisor (id_rol = 4)
        Usuario_Rol usuarioRol = usuarioRolRepository.findByUsuario_Persona_IdAndRol_Id(id, 28L)
                .orElseThrow(() -> new RuntimeException("El usuario no es un supervisor o no existe"));

        // Obtener la Persona y el Usuario asociados
        Persona persona = usuarioRol.getUsuario().getPersona();
        Usuario usuario = usuarioRol.getUsuario();

        // Actualizar los datos de Persona si han cambiado
        if (!persona.getNombre().equals(supervisorDTO.getNombre())) {
            persona.setNombre(supervisorDTO.getNombre());
        }
        if (!persona.getApellido().equals(supervisorDTO.getApellido())) {
            persona.setApellido(supervisorDTO.getApellido());
        }
        if (!persona.getEmail().equals(supervisorDTO.getEmail())) {
            if (personaRepository.existsByEmail(supervisorDTO.getEmail())) {
                throw new RuntimeException("El email ya está registrado.");
            }
            persona.setEmail(supervisorDTO.getEmail());
        }
        if (!persona.getDni().equals(supervisorDTO.getDni())) {
            if (personaRepository.existsByDni(supervisorDTO.getDni())) {
                throw new RuntimeException("El DNI ya está registrado.");
            }
            persona.setDni(supervisorDTO.getDni());
        }
        personaRepository.save(persona); // Guardar cambios en Persona

        // Actualizar los datos de Usuario si han cambiado
        if (!usuario.getUsername().equals(persona.getNombre())) {
            if (usuarioRepository.existsByUsername(persona.getNombre())) {
                throw new RuntimeException("El username ya está registrado.");
            }
            usuario.setUsername(persona.getNombre());
        }
        if (!encode.matches(supervisorDTO.getDni(), usuario.getPassword())) {
            usuario.setPassword(encode.encode(supervisorDTO.getDni())); // Encriptar contraseña
        }
        usuarioRepository.save(usuario); // Guardar cambios en Usuario
    }
}