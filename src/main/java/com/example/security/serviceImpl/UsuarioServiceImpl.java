package com.example.security.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.security.entity.Persona;
import com.example.security.entity.Usuario;
import com.example.security.repository.PersonaRepository;
import com.example.security.repository.UsuarioRepository;
import com.example.security.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Usuario create(Usuario usuario) {
        if (usuario.getPersona() != null && usuario.getPersona().getId() != null) {
            // Cargar la entidad Persona asociada
            Persona persona = personaRepository.findById(usuario.getPersona().getId())
                               .orElseThrow(() -> new RuntimeException("Persona no encontrada con ID: " + usuario.getPersona().getId()));
            usuario.setPersona(persona);

            // Ya no se verifica si la persona es un estudiante, ya que ahora puede ser cualquier tipo de persona.
        } else {
            throw new RuntimeException("ID de Persona es requerido para crear Usuario");
        }

        // Encriptar la contraseña antes de guardar el usuario
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario update(Usuario usuario) {
        if (usuario.getPersona() != null && usuario.getPersona().getId() != null) {
            Persona persona = personaRepository.findById(usuario.getPersona().getId())
                               .orElseThrow(() -> new RuntimeException("Persona no encontrada con ID: " + usuario.getPersona().getId()));
            usuario.setPersona(persona);
        }

        // Encriptar la contraseña antes de actualizar el usuario
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        return usuarioRepository.save(usuario);
    }

    @Override
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Optional<Usuario> read(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public List<Usuario> readAll() {
        return usuarioRepository.findAll();
    }
}
