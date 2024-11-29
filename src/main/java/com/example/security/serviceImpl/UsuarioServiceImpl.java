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
        // Verifica si la Persona está asociada al Usuario y si tiene un ID válido
        if (usuario.getPersona() != null && usuario.getPersona().getId() != null) {
            // Cargar la entidad Persona asociada
            Persona persona = personaRepository.findById(usuario.getPersona().getId())
                    .orElseThrow(() -> new RuntimeException("Persona no encontrada con ID: " + usuario.getPersona().getId()));
            usuario.setPersona(persona); // Asocia la persona al usuario
        } else {
            throw new IllegalArgumentException("ID de Persona es requerido para crear Usuario");
        }

        // Verifica y encripta la contraseña antes de guardar el usuario
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        } else {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }

        return usuarioRepository.save(usuario); // Guarda el usuario en la base de datos
    }

    @Override
    public Usuario update(Usuario usuario) {
        // Verifica si la Persona está asociada al Usuario y si tiene un ID válido
        if (usuario.getPersona() != null && usuario.getPersona().getId() != null) {
            Persona persona = personaRepository.findById(usuario.getPersona().getId())
                    .orElseThrow(() -> new RuntimeException("Persona no encontrada con ID: " + usuario.getPersona().getId()));
            usuario.setPersona(persona); // Asocia la persona al usuario
        }

        // Encriptar la contraseña antes de actualizar
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        } else {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }

        return usuarioRepository.save(usuario); // Guarda el usuario actualizado
    }

    @Override
    public void delete(Long id) {
        usuarioRepository.deleteById(id); // Elimina el usuario por su ID
    }

    @Override
    public Optional<Usuario> read(Long id) {
        return usuarioRepository.findById(id); // Devuelve el usuario por su ID
    }

    @Override
    public List<Usuario> readAll() {
        return usuarioRepository.findAll(); // Devuelve todos los usuarios
    }
}
