package com.example.security.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}



