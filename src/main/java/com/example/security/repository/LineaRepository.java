package com.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.security.entity.Linea;

@Repository
public interface LineaRepository extends JpaRepository<Linea, Long>{

}
