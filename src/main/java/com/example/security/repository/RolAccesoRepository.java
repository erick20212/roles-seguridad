package com.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.security.entity.Rol_Acceso;

@Repository
public interface RolAccesoRepository extends JpaRepository<Rol_Acceso,Long>{

}
