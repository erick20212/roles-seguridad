package com.example.security.repository;

import java.util.Optional;

import com.example.security.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.security.entity.Estudiante;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante,Long>{
	 // Buscar un estudiante por el ID de la persona asociada
    Optional<Estudiante> findByPersonaId(Long personaId);

    // Buscar un estudiante por su código único
    Optional<Estudiante> findByCodigo(String codigo);
    boolean existsByCodigo(String codigo);
    Estudiante findByPersona(Persona persona);


}
