package com.example.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.security.entity.Estudiante;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante,Long>{
	Optional<Estudiante> findByPersonaId(Long personaId);
	  Optional<Estudiante> findByCodigo(String codigo);
}
