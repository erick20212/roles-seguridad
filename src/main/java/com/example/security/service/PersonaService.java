package com.example.security.service;

import java.util.List;
import java.util.Optional;


import com.example.security.entity.Persona;

public interface PersonaService {
	
	Persona create(Persona a);
	Persona update(Persona a);
    void delete(Long id);
    Optional<Persona> read(Long id);
    List<Persona> readAll();
}
