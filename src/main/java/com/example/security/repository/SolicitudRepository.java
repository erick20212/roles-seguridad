package com.example.security.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.security.entity.Solicitud;

import java.sql.Date;
import java.util.List;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
    List<Solicitud> findByEstudianteId(Long estudianteId); // Filtrar por estudiante
    List<Solicitud> findByEstado(String estado); // Filtrar por estado
    
    @Query("SELECT s FROM Solicitud s WHERE LOWER(s.estado) = 'aceptado'")
    List<Solicitud> findSolicitudesAprobadas();
    
    @Query("SELECT s FROM Solicitud s WHERE s.estado = :estado")
    List<Solicitud> findSolicitudesByEstado(@Param("estado") String estado);
   

    

   

}


