package com.example.security.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.entity.Empresa;
import com.example.security.entity.Persona;
import com.example.security.entity.Representante;
import com.example.security.repository.EmpresaRepository;
import com.example.security.repository.PersonaRepository;
import com.example.security.repository.RepresentanteRepository;
import com.example.security.service.RepresentanteService;

@Service
public class RepresentanteServiceImpl implements RepresentanteService {

    @Autowired
    private RepresentanteRepository repository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Override
    public Representante create(Representante representante) {
        if (representante.getEmpresa() != null && representante.getEmpresa().getId() != null) {
            Empresa empresa = empresaRepository.findById(representante.getEmpresa().getId())
                              .orElseThrow(() -> new RuntimeException("Empresa no encontrada con ID: " + representante.getEmpresa().getId()));
            representante.setEmpresa(empresa);
        } else {
            throw new RuntimeException("ID de Empresa es requerido para crear Representante");
        }

        if (representante.getPersona() != null && representante.getPersona().getId() != null) {
            Persona persona = personaRepository.findById(representante.getPersona().getId())
                              .orElseThrow(() -> new RuntimeException("Persona no encontrada con ID: " + representante.getPersona().getId()));
            representante.setPersona(persona);
        } else {
            throw new RuntimeException("ID de Persona es requerido para crear Representante");
        }

        return repository.save(representante);
    }

    @Override
    public Representante update(Representante representante) {
        if (representante.getEmpresa() != null && representante.getEmpresa().getId() != null) {
            Empresa empresa = empresaRepository.findById(representante.getEmpresa().getId())
                              .orElseThrow(() -> new RuntimeException("Empresa no encontrada con ID: " + representante.getEmpresa().getId()));
            representante.setEmpresa(empresa);
        }

        if (representante.getPersona() != null && representante.getPersona().getId() != null) {
            Persona persona = personaRepository.findById(representante.getPersona().getId())
                              .orElseThrow(() -> new RuntimeException("Persona no encontrada con ID: " + representante.getPersona().getId()));
            representante.setPersona(persona);
        }

        return repository.save(representante);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Representante> read(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Representante> readAll() {
        return repository.findAll();
    }
}
