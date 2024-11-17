package com.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.security.entity.Accesos;


@Repository
public interface AccesosRepository extends JpaRepository<Accesos, Long>{
	Accesos findByNombre(String nombre); 
}
