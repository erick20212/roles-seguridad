package com.example.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.security.entity.Usuario_Rol;

@Repository
public interface Usuario_Rol_Repository extends JpaRepository<Usuario_Rol,Long>{
	List<Usuario_Rol> findByRolId(Long rolId);
    Optional<Usuario_Rol> findByUsuario_Persona_IdAndRol_Id(@Param("personaId") Long personaId, @Param("rolId") Long rolId);
}
