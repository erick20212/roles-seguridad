package com.example.security.service;

import com.example.security.entity.Linea;
import com.example.security.repository.LineaRepository;
import org.springframework.stereotype.Service;

@Service
public class LineaServicio {

    private final LineaRepository lineaRepository;

    // Constructor para inyección de dependencias
    public LineaServicio(LineaRepository lineaRepository) {
        this.lineaRepository = lineaRepository;
    }

    public Linea create(Linea linea) {
        // Establecer "Activo" como valor predeterminado si no se envía
        if (linea.getEstado() == null || linea.getEstado().isEmpty()) {
            linea.setEstado("Activo");
        }
        return lineaRepository.save(linea);
    }
}
