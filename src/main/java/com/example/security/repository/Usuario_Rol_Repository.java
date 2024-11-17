package com.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.security.entity.Usuario_Rol;

@Repository
public interface Usuario_Rol_Repository extends JpaRepository<Usuario_Rol,Long>{

}
