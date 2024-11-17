package com.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.security.entity.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long>{

}
