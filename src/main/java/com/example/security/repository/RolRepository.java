package com.example.security.repository;

import com.example.security.entity.Rol;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol,Long> {
	Optional<Rol> findByName(String name);
	
}
