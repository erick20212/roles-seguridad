package com.example.security.service;

import java.util.List;
import java.util.Optional;

import com.example.security.entity.Estudiante;

public interface EstudianteService {
	

	Estudiante create(Estudiante a);
	Estudiante update(Estudiante a);
    void delete(Long id);
    Optional<Estudiante> read(Long id);
    List<Estudiante> readAll();
}
