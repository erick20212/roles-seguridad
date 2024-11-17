package com.example.security.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.entity.Rol_Acceso;
import com.example.security.entity.Rol;
import com.example.security.entity.Accesos;
import com.example.security.repository.RolAccesoRepository;
import com.example.security.repository.RolRepository;
import com.example.security.repository.AccesosRepository;
import com.example.security.service.RolAccesoService;

@Service
public class RolAccesoServiceImpl implements RolAccesoService {

    @Autowired
    private RolAccesoRepository repository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private AccesosRepository accesosRepository;

    @Override
    public Rol_Acceso create(Rol_Acceso rolAcceso) {
        // Verificar si las entidades de Rol y Accesos están presentes en la base de datos
        if (rolAcceso.getRol() != null && rolAcceso.getRol().getId() != null) {
            Rol rol = rolRepository.findById(rolAcceso.getRol().getId())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + rolAcceso.getRol().getId()));
            rolAcceso.setRol(rol);
        } else {
            throw new RuntimeException("ID de Rol es requerido para crear Rol_Acceso");
        }

        if (rolAcceso.getAccesos() != null && rolAcceso.getAccesos().getId() != null) {
            Accesos accesos = accesosRepository.findById(rolAcceso.getAccesos().getId())
                    .orElseThrow(() -> new RuntimeException("Acceso no encontrado con ID: " + rolAcceso.getAccesos().getId()));
            rolAcceso.setAccesos(accesos);
        } else {
            throw new RuntimeException("ID de Acceso es requerido para crear Rol_Acceso");
        }

        // Guardar la entidad Rol_Acceso con las entidades relacionadas
        return repository.save(rolAcceso);
    }

    @Override
    public Rol_Acceso update(Rol_Acceso rolAcceso) {
        if (rolAcceso.getRol() != null && rolAcceso.getRol().getId() != null) {
            Rol rol = rolRepository.findById(rolAcceso.getRol().getId())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + rolAcceso.getRol().getId()));
            rolAcceso.setRol(rol);
        }

        if (rolAcceso.getAccesos() != null && rolAcceso.getAccesos().getId() != null) {
            Accesos accesos = accesosRepository.findById(rolAcceso.getAccesos().getId())
                    .orElseThrow(() -> new RuntimeException("Acceso no encontrado con ID: " + rolAcceso.getAccesos().getId()));
            rolAcceso.setAccesos(accesos);
        }

        return repository.save(rolAcceso);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Rol_Acceso> read(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Rol_Acceso> readAll() {
        return repository.findAll();
    }
}
