package com.example.security.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.security.entity.Solicitud;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud,Long>{
	List<Solicitud> findByEstudianteId(Long estudianteId); // Filtrar por estudiante
    List<Solicitud> findByEstado(String estado); // Filtrar por estado

}
