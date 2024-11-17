package com.example.security.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.security.entity.Solicitud;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud,Long>{
	

}
