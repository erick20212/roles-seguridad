package com.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.security.entity.Representante;

@Repository
public interface RepresentanteRepository extends JpaRepository<Representante,Long>{

}
