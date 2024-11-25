package com.example.security.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.entity.Persona;
import com.example.security.repository.PersonaRepository;
import com.example.security.service.PersonaService;

@Service
public class PersonaServiceImpl implements PersonaService {

	 @Autowired
	    private PersonaRepository repository;

	    @Override
	    public Persona create(Persona persona) {
	        return repository.save(persona);
	    }

	    @Override
	    public Persona update(Persona persona) {
	        // Cargar la entidad existente desde la base de datos
	        Optional<Persona> existingPersonaOpt = repository.findById(persona.getId());

	        if (existingPersonaOpt.isPresent()) {
	            Persona existingPersona = existingPersonaOpt.get();

	            // Actualizar solo los campos necesarios en la entidad existente
	            existingPersona.setNombre(persona.getNombre());
	            existingPersona.setApellido(persona.getApellido());
	            existingPersona.setEmail(persona.getEmail());
	            existingPersona.setDni(persona.getDni());
	            existingPersona.setEstado(persona.getEstado());

	            // Actualizar el usuario asociado si es necesario
	            if (persona.getUsuarios() != null) {
	                existingPersona.setUsuarios(persona.getUsuarios());
	            }

	            // Guardar la entidad actualizada en la base de datos
	            return repository.save(existingPersona);
	        } else {
	            // Si no se encuentra la persona, puedes manejarlo como prefieras; aquí lanzamos una excepción
	            throw new RuntimeException("Persona no encontrada con ID: " + persona.getId());
	        }
	    }

	    @Override
	    public void delete(Long id) {
	        repository.deleteById(id);
	    }

	    @Override
	    public Optional<Persona> read(Long id) {
	        return repository.findById(id);
	    }

	    @Override
	    public List<Persona> readAll() {
	        return repository.findAll();
	    }
	}
