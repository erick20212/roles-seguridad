package com.example.security.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.entity.Estudiante;
import com.example.security.entity.Persona;
import com.example.security.repository.EstudianteRepository;
import com.example.security.repository.PersonaRepository;
import com.example.security.service.EstudianteService;

@Service
public class EstudianteServiceImpl implements EstudianteService {

    @Autowired
    private EstudianteRepository repository;

    @Autowired
    private PersonaRepository personaRepository;

    @Override
    public Estudiante create(Estudiante estudiante) {
        if (estudiante.getPersona() != null && estudiante.getPersona().getId() != null) {
            // Cargar la persona usando el ID para evitar valores nulos en sus atributos
            Persona persona = personaRepository.findById(estudiante.getPersona().getId())
                               .orElseThrow(() -> new RuntimeException("Persona no encontrada con ID: " + estudiante.getPersona().getId()));
            estudiante.setPersona(persona);
        } else {
            throw new RuntimeException("ID de Persona es requerido para crear Estudiante");
        }

        // Guardar el estudiante con la persona asociada
        return repository.save(estudiante);
    }

    @Override
    public Estudiante update(Estudiante estudiante) {
        if (estudiante.getPersona() != null && estudiante.getPersona().getId() != null) {
            // Cargar la persona nuevamente en caso de que se quiera actualizar
            Persona persona = personaRepository.findById(estudiante.getPersona().getId())
                               .orElseThrow(() -> new RuntimeException("Persona no encontrada con ID: " + estudiante.getPersona().getId()));
            estudiante.setPersona(persona);
        }
        return repository.save(estudiante);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Estudiante> read(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Estudiante> readAll() {
        return repository.findAll();
    }
}
