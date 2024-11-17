package com.example.security.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.entity.Usuario_Rol;
import com.example.security.entity.Rol;
import com.example.security.entity.Usuario;
import com.example.security.repository.Usuario_Rol_Repository;
import com.example.security.repository.RolRepository;
import com.example.security.repository.UsuarioRepository;
import com.example.security.service.UsuarioRolService;

@Service
public class UsuarioRolServiceImpl implements UsuarioRolService {

    @Autowired
    private Usuario_Rol_Repository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Override
    public Usuario_Rol create(Usuario_Rol a) {
        // Verificar que el usuario y el rol existan
        if (a.getUsuario() != null && a.getUsuario().getId() != null) {
            Usuario usuario = usuarioRepository.findById(a.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + a.getUsuario().getId()));
            a.setUsuario(usuario);
        } else {
            throw new RuntimeException("ID de Usuario es requerido para crear Usuario_Rol");
        }

        if (a.getRol() != null && a.getRol().getId() != null) {
            Rol rol = rolRepository.findById(a.getRol().getId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + a.getRol().getId()));
            a.setRol(rol);
        } else {
            throw new RuntimeException("ID de Rol es requerido para crear Usuario_Rol");
        }

        // Guardar la relación
        return repository.save(a);
    }

    @Override
    public Usuario_Rol update(Usuario_Rol a) {
        // Verificar y actualizar usuario y rol de la misma manera
        if (a.getUsuario() != null && a.getUsuario().getId() != null) {
            Usuario usuario = usuarioRepository.findById(a.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + a.getUsuario().getId()));
            a.setUsuario(usuario);
        }

        if (a.getRol() != null && a.getRol().getId() != null) {
            Rol rol = rolRepository.findById(a.getRol().getId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + a.getRol().getId()));
            a.setRol(rol);
        }

        return repository.save(a);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Usuario_Rol> read(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Usuario_Rol> readAll() {
        return repository.findAll();
    }
}
