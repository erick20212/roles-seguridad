package com.example.security.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.security.entity.Linea;
import com.example.security.service.LineaService;

@RestController
@RequestMapping("/api/linea")
@CrossOrigin(origins = "http://localhost:4200") // Permite solicitudes desde Angular en localhost:4200
public class LineaController {

    @Autowired
    private LineaService lineaService;

    // Obtener todas las líneas
    @GetMapping
    public ResponseEntity<List<Linea>> readAll() {
        List<Linea> lista = lineaService.readAll();
        return lista.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // Crear una nueva línea
    @PostMapping
    public ResponseEntity<Linea> create(@RequestBody Linea linea) {
        // Establecer "Activo" como valor predeterminado para estado
        if (linea.getEstado() == null || linea.getEstado().isEmpty()) {
            linea.setEstado("Activo");
        }
        Linea nuevaLinea = lineaService.create(linea);
        return new ResponseEntity<>(nuevaLinea, HttpStatus.CREATED);
    }

    // Obtener una línea por ID
    @GetMapping("/{id}")
    public ResponseEntity<Linea> read(@PathVariable Long id) {
        Optional<Linea> linea = lineaService.read(id);
        return linea.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Actualizar una línea existente
    @PutMapping("/{id}")
    public ResponseEntity<Linea> update(@PathVariable Long id, @RequestBody Linea linea) {
        Optional<Linea> existingLinea = lineaService.read(id);
        if (existingLinea.isPresent()) {
            linea.setId(id); // Asegurarse de que el ID no cambie
            Linea updatedLinea = lineaService.update(linea);
            return new ResponseEntity<>(updatedLinea, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar una línea por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Linea> existingLinea = lineaService.read(id);
        if (existingLinea.isPresent()) {
            lineaService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
